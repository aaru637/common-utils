package org.dk.patchable;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Core engine that uses reflection to discover {@link Patchable @Patchable}
 * fields
 * and build {@link PatchContext} instances.
 *
 * <p>
 * This class caches reflection results per class to avoid repeated scanning
 * overhead.
 * </p>
 *
 * <h2>Usage:</h2>
 *
 * <pre>{@code
 * // Create a context for an object — all patchable fields start as ABSENT
 * PatchContext ctx = PatchableFieldTracker.createContext(UserUpdateRequest.class);
 *
 * // During deserialization, mark fields that appear in the input
 * ctx.markPresent("name");
 * ctx.markPresent("age");
 *
 * // Query the context
 * ctx.getPresentFields(); // ["name", "age"]
 * ctx.getAbsentFields(); // ["email", "phone_number", "address"]
 * }</pre>
 *
 * @author Dhineshkumar Dhandapani
 * @version 2.0.3
 * <p>Created at : 2026-04-03</p>
 */
public final class PatchableFieldTracker {

    /**
     * Cache of patchable field metadata per class.
     * Key: the annotated class; Value: list of PatchableFieldInfo for that class.
     */
    private static final Map<Class<?>, List<PatchableFieldInfo>> FIELD_CACHE = new ConcurrentHashMap<>();

    /**
     * Patchable Field Tracker
     */
    private PatchableFieldTracker() {
        // Utility class — no instantiation
    }

    /**
     * Creates a new {@link PatchContext} for the given class with all patchable
     * fields
     * initialized to {@link FieldState#ABSENT}.
     *
     * @param clazz the class to scan for {@link Patchable @Patchable} fields
     * @return a new PatchContext with all patchable fields registered as ABSENT
     */
    public static PatchContext createContext(Class<?> clazz) {
        List<PatchableFieldInfo> fields = getPatchableFieldInfos(clazz);
        PatchContext context = new PatchContext();
        for (PatchableFieldInfo info : fields) {
            context.registerField(info.resolvedName());
        }
        return context;
    }

    /**
     * Returns the resolved field name for a given Java {@link Field}.
     * If the field has a {@link Patchable @Patchable} annotation with a non-empty
     * {@code name()},
     * that name is returned. Otherwise, the Java field name is used.
     *
     * @param field the Java reflection field
     * @return the resolved name for tracking purposes
     */
    public static String resolveFieldName(Field field) {
        Patchable annotation = field.getAnnotation(Patchable.class);
        if (annotation != null && !annotation.name().isEmpty()) {
            return annotation.name();
        }
        return field.getName();
    }

    /**
     * Returns all patchable fields from a class, including inherited fields.
     *
     * @param clazz the class to scan
     * @return an unmodifiable list of patchable Java fields
     */
    public static List<Field> getPatchableFields(Class<?> clazz) {
        return getPatchableFieldInfos(clazz).stream().map(PatchableFieldInfo::field).toList();
    }

    /**
     * Returns the cached list of patchable field info for the given class.
     *
     * @param clazz the class to scan
     * @return list of PatchableFieldInfo
     */
    public static List<PatchableFieldInfo> getPatchableFieldInfos(Class<?> clazz) {
        return FIELD_CACHE.computeIfAbsent(clazz, PatchableFieldTracker::scanClass);
    }

    /**
     * Builds a mapping from JSON/serialized field name to the resolved patchable
     * field name.
     * This is useful for integration modules (e.g., Jackson) that need to map
     * incoming
     * property names to the tracked field names.
     *
     * <p>
     * The mapping uses the Java field name as the key (which typically matches the
     * JSON property name) and the resolved patchable name as the value.
     * </p>
     *
     * @param clazz the class to scan
     * @return a map from Java field name to resolved patchable name
     */
    public static Map<String, String> getFieldNameMapping(Class<?> clazz) {
        List<PatchableFieldInfo> infos = getPatchableFieldInfos(clazz);
        Map<String, String> mapping = new java.util.LinkedHashMap<>();
        for (PatchableFieldInfo info : infos) {
            mapping.put(info.field().getName(), info.resolvedName());
            mapping.put(info.resolvedName(), info.resolvedName());
        }
        return Collections.unmodifiableMap(mapping);
    }

    /**
     * Scans a class for patchable fields. Supports two modes:
     * <ul>
     * <li><b>Class-level @Patchable:</b> All declared fields (including inherited)
     * are tracked.</li>
     * <li><b>Field-level @Patchable:</b> Only fields with the annotation are
     * tracked.</li>
     * </ul>
     */
    private static List<PatchableFieldInfo> scanClass(Class<?> clazz) {
        boolean classLevel = clazz.isAnnotationPresent(Patchable.class);
        List<PatchableFieldInfo> result = new ArrayList<>();

        // Walk up the class hierarchy to include inherited fields
        Class<?> current = clazz;
        while (current != null && current != Object.class) {
            for (Field field : current.getDeclaredFields()) {
                // Skip synthetic fields (e.g., generated by the compiler)
                if (field.isSynthetic()) {
                    continue;
                }

                // Skip the PatchContext field itself if the class implements PatchableAware
                if (PatchContext.class.isAssignableFrom(field.getType())) {
                    continue;
                }

                boolean fieldLevel = field.isAnnotationPresent(Patchable.class);

                if (classLevel || fieldLevel) {
                    String resolvedName = resolveFieldName(field);
                    result.add(new PatchableFieldInfo(field, resolvedName));
                }
            }
            current = current.getSuperclass();
        }

        return Collections.unmodifiableList(result);
    }

    /**
     * Holds metadata about a single patchable field.
     *
     * @param field        the Java reflection Field
     * @param resolvedName the name used for tracking (either the Java field name or
     *                     the @Patchable alias)
     */
    public record PatchableFieldInfo(Field field, String resolvedName) {}
}
