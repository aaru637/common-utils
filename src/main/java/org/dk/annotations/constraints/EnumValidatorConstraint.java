package org.dk.annotations.constraints;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.dk.CommonUtils;
import org.dk.TypeConverter;
import org.dk.annotations.EnumValidator;
import org.dk.exception.InvalidEnumException;

/**
 * Bean Validation constraint validator implementation for the {@link EnumValidator} annotation.
 * <p>
 * This validator checks whether a string value matches one of the constants defined in a specified
 * enum class. The validation is performed case-insensitively and supports null values by default
 * (following Bean Validation conventions where null values are considered valid unless explicitly
 * constrained by {@code @NotNull}).
 * <p>
 * <strong>Validation Logic:</strong>
 * <ol>
 *   <li>Null values are considered valid (return {@code true})</li>
 *   <li>Non-null values are compared case-insensitively against enum constant names</li>
 *   <li>If no match is found, an {@link InvalidEnumException} is thrown with response code</li>
 * </ol>
 * <p>
 * <strong>Usage with Bean Validation:</strong>
 * This validator is automatically invoked when the {@code @EnumValidator} annotation is
 * encountered during Bean Validation processing. It integrates with validation frameworks
 * like Hibernate Validator and Spring Validation.
 * <p>
 * <strong>Exception Handling:</strong>
 * Unlike standard Bean Validation patterns, this validator throws a custom exception
 * ({@link InvalidEnumException}) instead of returning {@code false}. This provides
 * additional error context including response codes and allowed values.
 *
 * @author Dhineshkumar Dhandapani
 * @version 1.0
 * @see EnumValidator
 * @see ConstraintValidator
 * @see InvalidEnumException
 * @since 1.0
 * <p>Created at : 2025-09-17</p>
 */
public class EnumValidatorConstraint implements ConstraintValidator<EnumValidator, String> {
    /**
     * The annotation instance containing validation configuration and metadata.
     */
    private EnumValidator annotation;

    /**
     * Initializes the validator with the annotation instance and its configuration.
     * <p>
     * This method is called once by the Bean Validation framework during validator
     * initialization. It stores the annotation instance to access configuration
     * parameters like enum class and response code during validation.
     * <p>
     * The stored annotation provides access to:
     * <ul>
     *   <li>{@code enumClass()} - The enum class containing valid constants</li>
     *   <li>{@code responseCode()} - Custom error code for exception handling</li>
     *   <li>{@code message()} - Default validation error message</li>
     * </ul>
     *
     * @param annotation the {@link EnumValidator} annotation instance containing
     *                   validation configuration and metadata
     * @see ConstraintValidator#initialize(java.lang.annotation.Annotation)
     */
    @Override
    public void initialize(EnumValidator annotation) {
        this.annotation = annotation;
    }

    /**
     * Validates whether the provided string value matches an enum constant from the configured enum class.
     * <p>
     * <strong>Validation Process:</strong>
     * <ol>
     *   <li><strong>Null Handling:</strong> Returns {@code true} for null values (Bean Validation convention)</li>
     *   <li><strong>Enum Retrieval:</strong> Obtains all constants from the configured enum class</li>
     *   <li><strong>Value Matching:</strong> Performs case-insensitive comparison against enum constant names</li>
     *   <li><strong>Exception Throwing:</strong> Throws {@link InvalidEnumException} if no match is found</li>
     * </ol>
     * <p>
     * <strong>Comparison Logic:</strong>
     * The method uses {@link CommonUtils#isStringsEqualWithIgnoreCase(String, String)} to perform
     * case-insensitive string comparison between the input value and each enum constant's
     * string representation (obtained via {@code toString()}).
     * <p>
     * <strong>Important Notes:</strong>
     * <ul>
     *   <li><strong>Null Safety:</strong> Null values are always considered valid</li>
     *   <li><strong>Case Insensitive:</strong> Comparison ignores case differences</li>
     *   <li><strong>Exception-Based:</strong> Throws custom exception instead of returning {@code false}</li>
     *   <li><strong>Empty String Handling:</strong> Empty strings are currently treated as valid due to logic flaw</li>
     * </ul>
     * <p>
     * <strong>Known Issues:</strong>
     * <ul>
     *   <li>The condition {@code CommonUtils.isEmpty(value) ||} causes empty strings to always return {@code true}</li>
     *   <li>Non-standard exception throwing instead of returning {@code false} for Bean Validation</li>
     * </ul>
     *
     * @param value   the string value to validate against enum constants, may be {@code null}
     * @param context the constraint validation context providing additional validation information
     *                and capabilities (currently unused in this implementation)
     * @return {@code true} if the value is null, empty, or matches an enum constant (case-insensitive);
     * never returns {@code false} as exceptions are thrown for invalid values
     * @throws InvalidEnumException if the value does not match any enum constant, containing
     *                              the configured response code and list of allowed values
     * @see CommonUtils#isNull(String)
     * @see CommonUtils#isEmpty(String)
     * @see CommonUtils#isStringsEqualWithIgnoreCase(String, String)
     * @see TypeConverter#arrayToString(Object[])
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (CommonUtils.isNull(value)) return true;
        Object[] allowedValues = annotation.enumClass().getEnumConstants();

        for (Object enumValue : allowedValues) {
            if (CommonUtils.isStringsEqualWithIgnoreCase(value, enumValue.toString())) {
                return true;
            }
        }
        throw new InvalidEnumException(annotation.responseCode(), TypeConverter.arrayToString(allowedValues));
    }
}
