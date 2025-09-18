package org.dk.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.dk.annotations.constraints.EnumValidatorConstraint;

import java.lang.annotation.*;

/**
 * Bean Validation annotation for validating field values against enum constants.
 * <p>
 * This annotation provides a flexible way to validate that a field's value matches
 * one of the constants defined in a specified enum class. It integrates with the
 * Bean Validation framework (JSR-303/JSR-380) to provide runtime validation with
 * customizable error messages and response codes.
 * <p>
 * <strong>Key Features:</strong>
 * <ul>
 *   <li><strong>Enum Validation:</strong> Ensures field values are valid enum constants</li>
 *   <li><strong>Flexible Enum Types:</strong> Works with any enum class specified at annotation time</li>
 *   <li><strong>Custom Error Codes:</strong> Configurable response codes for API error handling</li>
 *   <li><strong>Validation Groups:</strong> Supports Bean Validation groups for conditional validation</li>
 *   <li><strong>Custom Messages:</strong> Allows personalized validation error messages</li>
 * </ul>
 * <p>
 * <strong>Validation Behavior:</strong>
 * The validator checks if the annotated field's value corresponds to a valid constant
 * in the specified enum class. The validation is typically case-sensitive and requires
 * an exact match with enum constant names.
 * <p>
 * <strong>Usage Examples:</strong>
 * <pre>{@code
 * public class UserRegistration {
 *     @EnumValidator(enumClass = UserStatus.class,
 *                   message = "Invalid user status provided")
 *     private String status;
 *
 *     @EnumValidator(enumClass = Country.class,
 *                   responseCode = "COUNTRY_ERR",
 *                   message = "Please provide a valid country code")
 *     private String countryCode;
 *
 *     @EnumValidator(enumClass = Priority.class, groups = ValidationGroups.Update.class)
 *     private String priority;
 * }
 *
 * enum UserStatus { ACTIVE, INACTIVE, PENDING, SUSPENDED }
 * enum Country { US, UK, CA, IN, AU }
 * enum Priority { LOW, MEDIUM, HIGH, CRITICAL }
 * }</pre>
 *
 * @author Dhineshkumar Dhandapani
 * @version 1.0
 * @see EnumValidatorConstraint
 * @see jakarta.validation.Constraint
 * @see jakarta.validation.Payload
 * @since 1.0
 * <p>Created at : 2025-09-17</p>
 */
@Documented
@Constraint(validatedBy = EnumValidatorConstraint.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface EnumValidator {
    /**
     * Specifies the enum class against which the field value should be validated.
     * <p>
     * This parameter defines the enum type that contains the valid constants for validation.
     * The annotated field's value will be checked against the constants defined in this enum class.
     * The validator performs case-insensitive matching between the field value and enum constant names.
     * <p>
     * <strong>Required Parameter:</strong> This is the only mandatory parameter that must be specified
     * when using the {@code @EnumValidator} annotation.
     * <p>
     * <strong>Example Usage:</strong>
     * <pre>{@code
     * @EnumValidator(enumClass = Status.class)
     * private String userStatus; // Valid values: "ACTIVE", "inactive", "Pending", etc.
     *
     * @EnumValidator(enumClass = UserRole.class)
     * private String role; // Valid values from UserRole enum constants
     * }</pre>
     *
     * @return the enum class containing valid constants for validation
     */
    Class<? extends Enum<?>> enumClass();

    /**
     * Specifies a custom response code for validation failures.
     * <p>
     * This parameter allows you to define application-specific error codes that can be
     * used for API responses, error logging, or client-side error handling. The response
     * code provides a structured way to identify different types of validation failures
     * across your application.
     * <p>
     * <strong>Common Use Cases:</strong>
     * <ul>
     *   <li>REST API error responses with specific error codes</li>
     *   <li>Error categorization in logging systems</li>
     *   <li>Client-side error handling and user feedback</li>
     *   <li>Integration with monitoring and alerting systems</li>
     * </ul>
     * <p>
     * <strong>Default Value:</strong> {@code "ERR01"} - A generic error code that can be
     * overridden for more specific error identification.
     *
     * @return the custom response code for validation failures
     */
    String responseCode() default "ERR01";

    /**
     * Defines the validation error message to be displayed when validation fails.
     * <p>
     * This message will be returned by the validation framework when the field value
     * does not match any of the enum constants. The message can be customized to provide
     * user-friendly feedback, technical details for debugging, or localized content.
     * <p>
     * <strong>Message Interpolation:</strong>
     * The message supports Bean Validation's message interpolation features, allowing
     * for parameterized messages and internationalization through message bundles.
     * You can use expressions like {@code {validatedValue}} to include the invalid value
     * in the error message.
     * <p>
     * <strong>Examples:</strong>
     * <pre>{@code
     * // Basic custom message
     * @EnumValidator(enumClass = Status.class,
     *               message = "Status must be ACTIVE, INACTIVE, or PENDING")
     *
     * // Message with interpolation
     * @EnumValidator(enumClass = Priority.class,
     *               message = "'{validatedValue}' is not a valid priority level")
     *
     * // Localized message key
     * @EnumValidator(enumClass = Country.class,
     *               message = "{validation.country.invalid}")
     * }</pre>
     *
     * @return the error message for validation failures
     * @see jakarta.validation.ConstraintValidator
     */
    String message() default "Invalid value for enum";

    /**
     * Specifies validation groups for conditional validation scenarios.
     * <p>
     * Groups allow you to define different validation contexts where this constraint
     * should be applied. This is particularly useful when you need different validation
     * rules for different operations such as create vs. update operations, or different
     * user roles and permissions.
     * <p>
     * <strong>Default Behavior:</strong>
     * When no groups are specified, the constraint applies to the default validation group
     * ({@code javax.validation.groups.Default}), which means it will be validated in
     * standard validation scenarios.
     * <p>
     * <strong>Usage Examples:</strong>
     * <pre>{@code
     * public class UserProfile {
     *     // Always validated
     *     @EnumValidator(enumClass = UserStatus.class)
     *     private String status;
     *
     *     // Only validated during creation
     *     @EnumValidator(enumClass = UserType.class, groups = ValidationGroups.Create.class)
     *     private String userType;
     *
     *     // Validated during both create and update operations
     *     @EnumValidator(enumClass = Priority.class,
     *                   groups = {ValidationGroups.Create.class, ValidationGroups.Update.class})
     *     private String priority;
     * }
     * }</pre>
     *
     * @return the validation groups for which this constraint applies
     * @see jakarta.validation.groups.Default
     */
    Class<?>[] groups() default {};

    /**
     * Specifies payload information for advanced validation scenarios.
     * <p>
     * Payload allows you to attach additional metadata to the constraint that can be
     * used by validation clients for specialized processing. This is typically used
     * for categorizing constraints, providing severity levels, or supplying custom
     * processing hints for validation frameworks.
     * <p>
     * <strong>Common Use Cases:</strong>
     * <ul>
     *   <li><strong>Severity Levels:</strong> Categorizing violations as warnings vs. errors</li>
     *   <li><strong>Validation Categories:</strong> Grouping related validation types</li>
     *   <li><strong>Processing Hints:</strong> Providing framework-specific configuration</li>
     *   <li><strong>Error Reporting:</strong> Customizing how violations are reported</li>
     * </ul>
     * <p>
     * <strong>Example Usage:</strong>
     * <pre>{@code
     * // Using severity payload
     * @EnumValidator(enumClass = Priority.class,
     *               payload = {Severity.Error.class})
     * private String priority;
     *
     * // Using custom processing payload
     * @EnumValidator(enumClass = Category.class,
     *               payload = {DatabaseValidation.class, CacheInvalidation.class})
     * private String category;
     * }</pre>
     *
     * @return the payload classes associated with this constraint
     * @see jakarta.validation.Payload
     */
    Class<? extends Payload>[] payload() default {};
}
