package org.dk.annotations.dto;

import org.dk.annotations.Patchable;
import org.dk.annotations.deserializer.FieldPresenceDeserializer;

import java.util.HashMap;
import java.util.Map;

/**
 * Abstract base class for DTOs that need to track which fields
 * were explicitly present in a JSON payload during deserialization.
 *
 * <p>When combined with {@link FieldPresenceDeserializer} and {@link Patchable},
 * this class allows you to implement "partial update" logic cleanly by
 * distinguishing between missing fields and fields explicitly set to {@code null}.</p>
 *
 * <p><b>Example usage:</b></p>
 * <pre>
 * public class UserRequest extends FieldPresenceChecker {
 *     {@code @Patchable}
 *     private String username;
 *
 *     {@code @Patchable}
 *     private String bio;
 * }
 *
 * UserRequest req = ...;
 * if (req.isFieldPresent("bio")) {
 *     // bio was explicitly provided (even if null)
 * }
 * </pre>
 *
 * @author Dhineshkumar Dhandapani
 * @version 1.0
 * @since 1.0
 * <p>Created at : 2025-08-31</p>
 */
public abstract class FieldPresenceChecker {

    private final Map<String, Boolean> fieldPresence = new HashMap<>();

    /**
     * Marks a field as present in the incoming JSON.
     *
     * @param fieldName the field name
     */
    public void markField(String fieldName, boolean isPresent) {
        fieldPresence.put(fieldName, isPresent);
    }

    /**
     * Checks if a field was explicitly present in the JSON.
     *
     * @param fieldName the field name
     * @return true if the field was present, false otherwise
     */
    public boolean isFieldPresent(String fieldName) {
        return fieldPresence.getOrDefault(fieldName, false);
    }
}
