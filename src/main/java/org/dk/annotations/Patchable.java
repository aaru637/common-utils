package org.dk.annotations;

import org.dk.annotations.deserializer.FieldPresenceDeserializer;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation used to mark DTO fields as "patchable".
 *
 * <p>When applied to a field, the {@link FieldPresenceDeserializer}
 * will record whether that field was present in the JSON payload,
 * even if its value is {@code null}. This makes it possible to
 * distinguish between "not provided" and "explicitly set to null".</p>
 *
 * <p><b>Example usage:</b></p>
 * <pre>
 * public class UserRequest extends FieldPresenceChecker {
 *     {@code @Patchable}
 *     private String email;
 * }
 * </pre>
 *
 * <p>Now, during PATCH operations:</p>
 * <ul>
 *   <li>If {@code email} is missing in the JSON → leave unchanged</li>
 *   <li>If {@code email} is {@code null} → clear it</li>
 *   <li>If {@code email} has a value → update it</li>
 * </ul>
 *
 * @author Dhineshkumar Dhandapani
 * @version 1.0
 * @since 1.0
 * <p>Created at : 2025-08-31</p>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Patchable {
}
