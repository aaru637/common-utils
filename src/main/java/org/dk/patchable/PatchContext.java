package org.dk.patchable;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Holds the per-object tracking state for patchable fields.
 *
 * <p>A {@code PatchContext} is created during deserialization (or programmatically)
 * and tracks which fields were present in the incoming request vs. which were absent.</p>
 *
 * <h2>Example usage:</h2>
 * <pre>{@code
 * PatchContext ctx = request.getPatchContext();
 *
 * if (ctx.isPresent("name")) {
 *     entity.setName(request.getName());
 * }
 * // "email" was not in the request — leave it unchanged
 * if (ctx.isPresent("email")) {
 *     entity.setEmail(request.getEmail());
 * }
 * }</pre>
 *
 * @author Dhineshkumar Dhandapani
 * @version 2.0.3
 * <p>Created at : 2026-04-03</p>
 */
public class PatchContext {

    private final Map<String, FieldState> fieldStates;

    /**
     * Creates a new PatchContext with the given initial field states.
     *
     * @param fieldStates a map of field names to their initial states
     */
    public PatchContext(Map<String, FieldState> fieldStates) {
        this.fieldStates = new LinkedHashMap<>(fieldStates);
    }

    /**
     * Creates an empty PatchContext. Fields can be registered later via {@link #registerField(String)}.
     */
    public PatchContext() {
        this.fieldStates = new LinkedHashMap<>();
    }

    /**
     * Registers a field with an initial state of {@link FieldState#ABSENT}.
     *
     * @param fieldName the name of the field to register
     */
    public void registerField(String fieldName) {
        fieldStates.putIfAbsent(fieldName, FieldState.ABSENT);
    }

    /**
     * Marks a field as {@link FieldState#PRESENT}, indicating it was provided in the request.
     *
     * @param fieldName the name of the field to mark as present
     * @throws IllegalArgumentException if the field is not registered in this context
     */
    public void markPresent(String fieldName) {
        if (!fieldStates.containsKey(fieldName)) {
            throw new IllegalArgumentException(
                    "Field '" + fieldName + "' is not registered as a patchable field. " + "Registered fields: " + fieldStates.keySet());
        }
        fieldStates.put(fieldName, FieldState.PRESENT);
    }

    /**
     * Checks if a field was present in the request.
     *
     * @param fieldName the name of the field to check
     * @return {@code true} if the field was explicitly provided, {@code false} otherwise
     */
    public boolean isPresent(String fieldName) {
        return fieldStates.get(fieldName) == FieldState.PRESENT;
    }

    /**
     * Checks if a field was absent from the request.
     *
     * @param fieldName the name of the field to check
     * @return {@code true} if the field was not provided, {@code false} otherwise
     */
    public boolean isAbsent(String fieldName) {
        FieldState state = fieldStates.get(fieldName);
        return state == null || state == FieldState.ABSENT;
    }

    /**
     * Returns the set of field names that were present in the request.
     *
     * @return an unmodifiable set of present field names
     */
    public Set<String> getPresentFields() {
        return fieldStates.entrySet().stream().filter(e -> e.getValue() == FieldState.PRESENT).map(Map.Entry::getKey).collect(
                Collectors.toUnmodifiableSet());
    }

    /**
     * Returns the set of field names that were absent from the request.
     *
     * @return an unmodifiable set of absent field names
     */
    public Set<String> getAbsentFields() {
        return fieldStates.entrySet().stream().filter(e -> e.getValue() == FieldState.ABSENT).map(Map.Entry::getKey).collect(
                Collectors.toUnmodifiableSet());
    }

    /**
     * Returns an unmodifiable view of all field states.
     *
     * @return a map of field names to their {@link FieldState}
     */
    public Map<String, FieldState> getAllFieldStates() {
        return Collections.unmodifiableMap(fieldStates);
    }

    /**
     * Returns the total number of tracked fields.
     *
     * @return the number of registered patchable fields
     */
    public int size() {
        return fieldStates.size();
    }

    @Override
    public String toString() {
        return "PatchContext{" + "present=" + getPresentFields() + ", absent=" + getAbsentFields() + '}';
    }
}
