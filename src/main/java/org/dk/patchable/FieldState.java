package org.dk.patchable;

/**
 * Represents the state of a patchable field during request processing.
 *
 * <ul>
 *   <li>{@link #PRESENT} — The field was explicitly provided in the incoming request.</li>
 *   <li>{@link #ABSENT} — The field was not included in the incoming request.</li>
 * </ul>
 *
 * <p>This distinction is critical for partial update (PATCH) semantics:</p>
 * <ul>
 *   <li>A {@code PRESENT} field should be updated to the provided value (even if {@code null}).</li>
 *   <li>An {@code ABSENT} field should remain unchanged in the target entity.</li>
 * </ul>
 *
 * @author Dhineshkumar Dhandapani
 * @version 2.0.3
 * <p>Created at : 2026-04-03</p>
 */
public enum FieldState {

    /**
     * The field was explicitly provided in the request.
     * The value should be applied to the target entity, even if it is {@code null}.
     */
    PRESENT,

    /**
     * The field was not included in the request.
     * The existing value in the target entity should be preserved.
     */
    ABSENT
}
