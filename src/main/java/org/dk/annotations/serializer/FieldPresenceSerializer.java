package org.dk.annotations.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.dk.annotations.dto.FieldPresenceChecker;

import java.io.IOException;
import java.lang.reflect.Field;

/**
 * Custom Jackson serializer that only serializes fields that were
 * marked as present in the {@link FieldPresenceChecker}.
 *
 * @param <T> the DTO type extending {@link FieldPresenceChecker}
 * @author Dhineshkumar Dhandapani
 * @version 1.0
 * @since 1.0
 */
public class FieldPresenceSerializer<T extends FieldPresenceChecker> extends JsonSerializer<T> {

    @Override
    public void serialize(T value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        
        Class<?> clazz = value.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            String fieldName = field.getName();
            if (value.isFieldPresent(fieldName)) {
                try {
                    boolean wasAccessible = field.canAccess(value);
                    if (!wasAccessible) {
                        field.setAccessible(true);
                    }
                    
                    Object fieldValue = field.get(value);
                    gen.writeObjectField(fieldName, fieldValue);
                    
                    if (!wasAccessible) {
                        field.setAccessible(false);
                    }
                } catch (IllegalAccessException e) {
                    throw new IOException("Failed to access field: " + fieldName, e);
                }
            }
        }
        
        gen.writeEndObject();
    }
}
