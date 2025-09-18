package org.dk.annotations.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import org.dk.annotations.Patchable;
import org.dk.annotations.dto.FieldPresenceChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Custom Jackson deserializer that supports detecting whether
 * fields annotated with {@link Patchable} are present in the JSON payload.
 *
 * <p>This enables "PATCH-like" DTOs where you can distinguish between:</p>
 * <ul>
 *   <li>Field missing &rarr; do not update</li>
 *   <li>Field explicitly set to {@code null} &rarr; clear the value</li>
 * </ul>
 *
 * <p>This deserializer works in combination with {@link FieldPresenceChecker}
 * which keeps track of field presence, and {@link Patchable}, which marks
 * which fields should be tracked.</p>
 *
 * <p><b>Example usage:</b></p>
 * <pre>
 * public class UserRequest extends FieldPresenceChecker {
 *     {@code @Patchable}
 *     private String email;
 *
 *     {@code @Patchable}
 *     private String phone;
 * }
 *
 * // In controller:
 * {@code @PatchMapping("/user")}
 * public ResponseEntity&lt;?&gt; updateUser(@RequestBody UserRequest request) {
 *     if (request.isFieldPresent("email")) {
 *         // update email
 *     }
 * }
 * </pre>
 *
 * @param <T> the DTO type extending {@link FieldPresenceChecker}
 * @author Dhineshkumar Dhandapani
 * @version 1.0
 * @since 1.0
 * <p>Created at : 2025-08-31</p>
 */
public class FieldPresenceDeserializer<T extends FieldPresenceChecker>
        extends JsonDeserializer<T> implements ContextualDeserializer {

    private static final Logger logger = LoggerFactory.getLogger(FieldPresenceDeserializer.class);

    private final Class<T> clazz;
    private static final Map<Class<?>, FieldCache> FIELD_CACHE = new ConcurrentHashMap<>();

    /**
     * Default constructor required by Jackson. Will be replaced via {@link #createContextual}.
     */
    public FieldPresenceDeserializer() {
        this.clazz = null;
    }

    /**
     * Constructor with target class.
     *
     * @param clazz the class type that this deserializer should handle
     */
    public FieldPresenceDeserializer(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public T deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        if (clazz == null) {
            throw JsonMappingException.from(parser, "Deserializer not contextualized with target class");
        }

        try {
            JsonNode node = parser.getCodec().readTree(parser);

            FieldCache fieldCache = getOrCreateFieldCache(clazz);
            T obj = createInstance();

            processPatchableFields(obj, node, parser, fieldCache.patchableFields);
            processRegularFields(obj, node, parser, fieldCache.regularFields);

            return obj;

        } catch (Exception e) {
            throw JsonMappingException.from(parser,
                    "Failed to deserialize " + clazz.getSimpleName() + ": " + e.getMessage(), e);
        }
    }

    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext context, BeanProperty property)
            throws JsonMappingException {
        JavaType type = context.getContextualType();
        if (type == null) {
            throw JsonMappingException.from(context, "Unable to determine target type for FieldPresenceDeserializer");
        }

        @SuppressWarnings("unchecked")
        Class<T> rawClass = (Class<T>) type.getRawClass();
        return new FieldPresenceDeserializer<>(rawClass);
    }

    private FieldCache getOrCreateFieldCache(Class<T> targetClass) {
        return FIELD_CACHE.computeIfAbsent(targetClass, this::createFieldCache);
    }

    private FieldCache createFieldCache(Class<?> targetClass) {
        try {
            Field[] declaredFields = targetClass.getDeclaredFields();

            List<Field> patchableFields = Arrays.stream(declaredFields)
                    .filter(field -> field.isAnnotationPresent(Patchable.class))
                    .collect(Collectors.toList());

            List<Field> regularFields = Arrays.stream(declaredFields)
                    .filter(field -> !field.isAnnotationPresent(Patchable.class))
                    .collect(Collectors.toList());

            return new FieldCache(patchableFields, regularFields);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create field cache for " + targetClass.getSimpleName(), e);
        }
    }

    private T createInstance() throws ReflectiveOperationException {
        try {
            return Objects.requireNonNull(clazz).getDeclaredConstructor().newInstance();
        } catch (NoSuchMethodException e) {
            throw new ReflectiveOperationException("Class " + Objects.requireNonNull(clazz).getSimpleName() +
                    " must have a default constructor", e);
        }
    }

    private void processPatchableFields(T obj, JsonNode node, JsonParser parser, List<Field> patchableFields)
            throws IOException, ReflectiveOperationException {
        for (Field field : patchableFields) {
            String fieldName = field.getName();
            boolean isPresent = node.has(fieldName);

            if (isPresent) {
                obj.markField(fieldName, Boolean.TRUE);
            } else {
                obj.markField(fieldName, Boolean.FALSE);
            }
            setFieldValue(obj, field, node.get(fieldName), parser);
        }
    }

    private void processRegularFields(T obj, JsonNode node, JsonParser parser, List<Field> regularFields)
            throws IOException, ReflectiveOperationException {
        for (Field field : regularFields) {
            String fieldName = field.getName();

            if (node.has(fieldName)) {
                setFieldValue(obj, field, node.get(fieldName), parser);
            }
        }
    }

    private void setFieldValue(T obj, Field field, JsonNode fieldNode, JsonParser parser)
            throws IOException, ReflectiveOperationException {
        boolean wasAccessible = field.canAccess(obj);

        try {
            if (!wasAccessible) {
                field.setAccessible(true);
            }

            Object value = parser.getCodec().treeToValue(fieldNode, field.getType());
            field.set(obj, value);

        } finally {
            if (!wasAccessible) {
                field.setAccessible(false);
            }
        }
    }

    /**
     * Simple field cache holder for patchable and regular fields.
     */
    private record FieldCache(List<Field> patchableFields, List<Field> regularFields) {
    }
}
