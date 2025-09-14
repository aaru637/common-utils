package org.dk;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class JsonUtilsTest {

    private final Person person = new Person("Alice", 30, new Date());

    @Test
    void testToJsonDefault() {
        String json = JsonUtils.toJson(person);
        assertNotNull(json);
        assertTrue(json.contains("Alice"));
    }

    @Test
    void testToJsonNull() {
        assertNull(JsonUtils.toJson(null));
    }

    @Test
    void testToJsonCustomFormat() {
        String json = JsonUtils.toJson(person, "yyyy-MM-dd");
        assertNotNull(json);
    }

    @Test
    void testToJsonInvalidFormat() {
        assertThrows(IllegalArgumentException.class,
                () -> JsonUtils.toJson(person, "invalid-format"));
    }

    @Test
    void testToJsonPrettyDefault() {
        String json = JsonUtils.toJsonPretty(person);
        assertNotNull(json);
        assertTrue(json.contains("\n")); // pretty means line breaks
    }

    @Test
    void testToJsonPrettyCustom() {
        String json = JsonUtils.toJsonPretty(person, "yyyy-MM-dd");
        assertNotNull(json);
        assertTrue(json.contains("\n"));
    }

    @Test
    void testFromJsonDefault() {
        String json = JsonUtils.toJson(person);
        Person result = JsonUtils.fromJson(json, Person.class);
        assertNotNull(result);
        assertEquals("Alice", result.getName());
    }

    @Test
    void testFromJsonNull() {
        assertNull(JsonUtils.fromJson(null, Person.class));
    }

    @Test
    void testFromJsonPrettyEnabled() {
        String json = JsonUtils.toJson(person);
        Person result = JsonUtils.fromJson(json, Person.class, true);
        assertNotNull(result);
        assertEquals("Alice", result.getName());
    }

    @Test
    void testFromJsonCustomFormat() {
        String json = JsonUtils.toJson(person, "yyyy-MM-dd");
        Person result = JsonUtils.fromJson(json, Person.class, "yyyy-MM-dd");
        assertNotNull(result);
    }

    @Test
    void testFromJsonCustomInvalidFormat() {
        String json = JsonUtils.toJson(person);
        assertThrows(IllegalArgumentException.class,
                () -> JsonUtils.fromJson(json, Person.class, "bad-format"));
    }

    @Test
    void testFromJsonCustomPretty() {
        String json = JsonUtils.toJson(person);
        Person result = JsonUtils.fromJson(json, Person.class, "yyyy-MM-dd", true);
        assertNotNull(result);
    }

    @Test
    void testFromJsonListDefault() {
        List<Person> list = List.of(person);
        String json = JsonUtils.fromListToJson(list);
        List<Person> result = JsonUtils.fromJsonList(json, Person.class);
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void testFromJsonListNull() {
        assertNull(JsonUtils.fromJsonList(null, Person.class));
    }

    @Test
    void testFromJsonListCustomFormat() {
        List<Person> list = List.of(person);
        String json = JsonUtils.fromListToJson(list, "yyyy-MM-dd");
        List<Person> result = JsonUtils.fromJsonList(json, Person.class, "yyyy-MM-dd");
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void testFromJsonListInvalidFormat() {
        String json = JsonUtils.fromListToJson(List.of(person));
        assertThrows(IllegalArgumentException.class,
                () -> JsonUtils.fromJsonList(json, Person.class, "bad-format"));
    }

    @Test
    void testFromJsonListPretty() {
        List<Person> list = List.of(person);
        String json = JsonUtils.fromListToJson(list);
        List<Person> result = JsonUtils.fromJsonList(json, Person.class, true);
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void testFromJsonListCustomPretty() {
        List<Person> list = List.of(person);
        String json = JsonUtils.fromListToJson(list, "yyyy-MM-dd");
        List<Person> result = JsonUtils.fromJsonList(json, Person.class, "yyyy-MM-dd", true);
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void testFromJsonMapDefault() {
        Map<String, String> map = new HashMap<>();
        map.put("k", "v");
        String json = JsonUtils.toJson(map);
        Map<String, String> result = JsonUtils.fromJsonMap(json, String.class, String.class);
        assertNotNull(result);
        assertEquals("v", result.get("k"));
    }

    @Test
    void testFromJsonMapNull() {
        assertNull(JsonUtils.fromJsonMap(null, String.class, String.class));
    }

    @Test
    void testFromJsonMapCustomFormat() {
        Map<String, String> map = Collections.singletonMap("k", "v");
        String json = JsonUtils.toJson(map, "yyyy-MM-dd");
        Map<String, String> result = JsonUtils.fromJsonMap(json, String.class, String.class, "yyyy-MM-dd");
        assertNotNull(result);
        assertEquals("v", result.get("k"));
    }

    @Test
    void testFromJsonMapInvalidFormat() {
        String json = JsonUtils.toJson(Collections.singletonMap("a", "b"));
        assertThrows(IllegalArgumentException.class,
                () -> JsonUtils.fromJsonMap(json, String.class, String.class, "bad-format"));
    }

    @Test
    void testFromJsonMapPretty() {
        Map<String, String> map = Collections.singletonMap("k", "v");
        String json = JsonUtils.toJson(map);
        Map<String, String> result = JsonUtils.fromJsonMap(json, String.class, String.class, true);
        assertNotNull(result);
        assertEquals("v", result.get("k"));
    }

    @Test
    void testFromJsonMapCustomPretty() {
        Map<String, String> map = Collections.singletonMap("k", "v");
        String json = JsonUtils.toJson(map, "yyyy-MM-dd");
        Map<String, String> result = JsonUtils.fromJsonMap(json, String.class, String.class, "yyyy-MM-dd", true);
        assertNotNull(result);
        assertEquals("v", result.get("k"));
    }

    @Test
    void testFromJsonToStringDefault() {
        String json = JsonUtils.toJson("hello");
        String str = JsonUtils.fromJsonToString(json);
        assertEquals("hello", str);
    }

    @Test
    void testFromJsonToStringNull() {
        assertNull(JsonUtils.fromJsonToString(null));
    }

    @Test
    void testFromJsonToStringCustomFormat() {
        String json = JsonUtils.toJson("hello", "yyyy-MM-dd");
        String str = JsonUtils.fromJsonToString(json, "yyyy-MM-dd");
        assertEquals("hello", str);
    }

    @Test
    void testFromJsonToStringInvalidFormat() {
        String json = JsonUtils.toJson("hello");
        assertThrows(IllegalArgumentException.class,
                () -> JsonUtils.fromJsonToString(json, "oops"));
    }

    @Test
    void testFromJsonToStringPretty() {
        String json = JsonUtils.toJson("hello");
        String str = JsonUtils.fromJsonToString(json, true);
        assertEquals("hello", str);
    }

    @Test
    void testFromJsonToStringCustomPretty() {
        String json = JsonUtils.toJson("hello", "yyyy-MM-dd");
        String str = JsonUtils.fromJsonToString(json, "yyyy-MM-dd", true);
        assertEquals("hello", str);
    }

    @Test
    void testFromListToJsonDefault() {
        List<String> list = Arrays.asList("a", "b");
        String json = JsonUtils.fromListToJson(list);
        assertNotNull(json);
        assertTrue(json.contains("a"));
    }

    @Test
    void testFromListToJsonNull() {
        assertNull(JsonUtils.fromListToJson(null));
    }

    @Test
    void testFromListToJsonCustomFormat() {
        List<String> list = Arrays.asList("a", "b");
        String json = JsonUtils.fromListToJson(list, "yyyy-MM-dd");
        assertNotNull(json);
        assertTrue(json.contains("a"));
    }

    @Test
    void testFromListToJsonInvalidFormat() {
        List<String> list = Arrays.asList("a", "b");
        assertThrows(IllegalArgumentException.class,
                () -> JsonUtils.fromListToJson(list, "bad-format"));
    }

    @Test
    void testFromListToJsonPrettyDefault() {
        List<String> list = Arrays.asList("x", "y");
        String json = JsonUtils.fromListToJsonPretty(list);
        assertNotNull(json);
        assertTrue(json.contains("\n"));
    }

    @Test
    void testFromListToJsonPrettyCustom() {
        List<String> list = Arrays.asList("x", "y");
        String json = JsonUtils.fromListToJsonPretty(list, "yyyy-MM-dd");
        assertNotNull(json);
        assertTrue(json.contains("\n"));
    }

    @Test
    void testConvertPersonToSameClass() {
        Person result = JsonUtils.convertObjectToClass(person, Person.class);

        assertNotNull(result);
        assertEquals(person.getName(), result.getName());
        assertEquals(person.getAge(), result.getAge());
        assertEquals(person.getBirthDate().getTime(), result.getBirthDate().getTime());
    }

    @Test
    void testConvertPersonToDifferentClass() {
        Person result = JsonUtils.convertObjectToClass(person, Person.class);

        assertNotNull(result);
        assertEquals("Alice", result.getName());
        assertEquals(30, result.getAge());
    }

    @Test
    void testConvertNullObject() {
        Person result = JsonUtils.convertObjectToClass(null, Person.class);
        assertNull(result);
    }

    @Test
    void testInvalidConversionThrowsException() {
        assertThrows(Exception.class, () -> {
            JsonUtils.convertObjectToClass("invalid_json_object", Person.class);
        });
    }
}
