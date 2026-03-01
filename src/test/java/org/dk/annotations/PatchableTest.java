package org.dk.annotations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.dk.annotations.deserializer.FieldPresenceDeserializer;
import org.dk.annotations.dto.FieldPresenceChecker;
import org.dk.annotations.serializer.FieldPresenceSerializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PatchableTest {

    private ObjectMapper objectMapper;

    @Patchable
    @JsonDeserialize(using = FieldPresenceDeserializer.class)
    @JsonSerialize(using = FieldPresenceSerializer.class)
    public static class ClassAnnotatedDto extends FieldPresenceChecker {
        private String name;
        private Integer age;
        
        // Regular field that should NOT be patchable if we had a way to exclude it, 
        // but currently our logic treats all fields as patchable if class is annotated.
        private String status;

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public Integer getAge() { return age; }
        public void setAge(Integer age) { this.age = age; }
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
    }

    public static class FieldAnnotatedDto extends FieldPresenceChecker {
        @Patchable
        private String name;
        
        private Integer age;

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public Integer getAge() { return age; }
        public void setAge(Integer age) { this.age = age; }
    }

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        // Since FieldPresenceDeserializer uses ContextualDeserializer, 
        // it should work when applied via annotation or registered in module.
    }

    @Test
    void testClassLevelPatchableDeserialization() throws Exception {
        String json = "{\"name\": \"John\", \"age\": null}";
        ClassAnnotatedDto dto = objectMapper.readValue(json, ClassAnnotatedDto.class);

        assertTrue(dto.isFieldPresent("name"));
        assertTrue(dto.isFieldPresent("age"));
        assertFalse(dto.isFieldPresent("status"));
        
        assertEquals("John", dto.getName());
        assertNull(dto.getAge());
    }

    @Test
    void testFieldLevelPatchableDeserialization() throws Exception {
        // We need to register the deserializer specifically for this test if not using annotation on class
        SimpleModule module = new SimpleModule();
        module.addDeserializer(FieldAnnotatedDto.class, new FieldPresenceDeserializer<>(FieldAnnotatedDto.class));
        objectMapper.registerModule(module);

        String json = "{\"name\": \"John\", \"age\": 30}";
        FieldAnnotatedDto dto = objectMapper.readValue(json, FieldAnnotatedDto.class);

        assertTrue(dto.isFieldPresent("name"));
        assertFalse(dto.isFieldPresent("age"));
        
        assertEquals("John", dto.getName());
        assertEquals(30, dto.getAge());
    }

    @Test
    void testFieldPresenceSerialization() throws Exception {
        ClassAnnotatedDto dto = new ClassAnnotatedDto();
        dto.setName("Jane");
        dto.markField("name", true);
        // age is not present
        // status is not present

        String json = objectMapper.writeValueAsString(dto);
        
        assertTrue(json.contains("\"name\":\"Jane\""));
        assertFalse(json.contains("\"age\""));
        assertFalse(json.contains("\"status\""));
    }
    
    @Test
    void testSerializationWithExplicitNull() throws Exception {
        ClassAnnotatedDto dto = new ClassAnnotatedDto();
        dto.setAge(null);
        dto.markField("age", true);

        String json = objectMapper.writeValueAsString(dto);
        
        assertTrue(json.contains("\"age\":null"));
        assertFalse(json.contains("\"name\""));
    }
}
