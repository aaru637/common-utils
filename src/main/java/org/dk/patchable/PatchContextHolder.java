package org.dk.patchable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A {@link ThreadLocal}-based holder for {@link PatchContext} instances.
 *
 * <p>This is used when the deserialized DTO does not implement
 * {@link PatchableAware}. The Jackson deserializer stores
 * the context here, and consumers can retrieve it after deserialization.</p>
 *
 * <h2>Usage (for DTOs that don't implement PatchableAware):</h2>
 * <pre>{@code
 * // After deserialization
 * PatchContext ctx = PatchContextHolder.get(requestObject);
 *
 * // When done processing
 * PatchContextHolder.clear();
 * }</pre>
 *
 * <p><b>Important:</b> Always call {@link #clear()} when you're finished processing
 * the request to prevent memory leaks in servlet/thread-pool environments.</p>
 *
 * @author Dhineshkumar Dhandapani
 * @version 2.0.3
 * <p>Created at : 2026-04-03</p>
 */
public final class PatchContextHolder {

    private static final ThreadLocal<Map<Object, PatchContext>> HOLDER = ThreadLocal.withInitial(ConcurrentHashMap::new);

    private PatchContextHolder() {
        // Utility class — no instantiation
    }

    /**
     * Stores a {@link PatchContext} associated with a deserialized bean.
     *
     * @param bean    the deserialized object
     * @param context the patch context for that object
     */
    public static void set(Object bean, PatchContext context) {
        HOLDER.get().put(bean, context);
    }

    /**
     * Retrieves the {@link PatchContext} for a given deserialized bean.
     *
     * @param bean the deserialized object
     * @return the associated PatchContext, or {@code null} if not found
     */
    public static PatchContext get(Object bean) {
        return HOLDER.get().get(bean);
    }

    /**
     * Clears all stored contexts for the current thread.
     * <b>Must be called after request processing</b> to prevent memory leaks.
     */
    public static void clear() {
        HOLDER.remove();
    }
}
