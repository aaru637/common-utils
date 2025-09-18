package org.dk.annotations;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.dk.annotations.deserializer.StringNormalizeDeserializer;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Custom Jackson annotation for comprehensive string normalization during JSON deserialization.
 * <p>
 * This annotation provides a convenient way to apply multiple string processing operations
 * to fields during JSON deserialization, including trimming, case conversion, capitalization,
 * and null handling. The annotation leverages a custom deserializer to transform incoming
 * string values according to the specified configuration.
 * <p>
 * <strong>Supported Operations:</strong>
 * <ul>
 *   <li><strong>Trimming:</strong> Removes leading and trailing whitespace</li>
 *   <li><strong>Capitalization:</strong> Capitalizes the first letter of the string</li>
 *   <li><strong>Case Conversion:</strong> Converts string to uppercase, lowercase, or leaves unchanged</li>
 *   <li><strong>Null Handling:</strong> Controls behavior when encountering null values</li>
 * </ul>
 * <p>
 * <strong>Processing Order:</strong>
 * The operations are applied in the following sequence:
 * <ol>
 *   <li>Null value handling (if applicable)</li>
 *   <li>String trimming (if enabled)</li>
 *   <li>Capitalization (if enabled)</li>
 *   <li>Case conversion (if specified)</li>
 * </ol>
 * <p>
 * <strong>Usage Example:</strong>
 * <pre>{@code
 * public class UserProfile {
 *     @StringNormalizer(trim = true, capitalize = true)
 *     private String firstName;
 *
 *     @StringNormalizer(caseConversion = StringCase.UPPER, nullIfEmpty = true)
 *     private String countryCode;
 *
 *     @StringNormalizer(caseConversion = StringCase.LOWER)
 *     private String email;
 * }
 * }</pre>
 *
 * @author Dhineshkumar Dhandapani
 * @version 1.0
 * @see StringNormalizeDeserializer
 * @see JsonDeserialize
 * @since 1.0
 * <p>Created at : 2025-09-17</p>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@JacksonAnnotationsInside
@JsonDeserialize(using = StringNormalizeDeserializer.class)
public @interface StringNormalizer {
    /**
     * Specifies whether to trim leading and trailing whitespace from the string value.
     * <p>
     * When enabled, this operation removes all leading and trailing whitespace characters
     * (spaces, tabs, newlines, etc.) from the deserialized string value.
     *
     * @return {@code true} if trimming should be applied, {@code false} otherwise
     * @see String#trim()
     */
    boolean trim() default true;

    /**
     * Determines the behavior when the deserialized value is {@code null}.
     * <p>
     * When {@code true}, null input values will be converted to an empty string.
     * When {@code false} (default), null input values remain as {@code null}.
     *
     * @return {@code true} to convert null values to empty strings, {@code false} to preserve null values
     */
    boolean nullIfEmpty() default false;

    /**
     * Specifies the case conversion strategy to apply to the string value.
     * <p>
     * This operation is applied after trimming and capitalization (if enabled).
     * The case conversion affects the entire string content.
     *
     * @return the case conversion strategy to apply
     * @see StringCase
     */
    StringCase caseConversion() default StringCase.NONE;

    /**
     * Specifies whether to capitalize the first letter of the string value.
     * <p>
     * When enabled, this operation capitalizes the first character of the string
     * while leaving the rest of the string unchanged. This operation is applied
     * after trimming but before case conversion.
     * <p>
     * <strong>Note:</strong> The exact capitalization behavior depends on the
     * implementation of {@code CommonUtils.capitalize()}.
     *
     * @return {@code true} if the first letter should be capitalized, {@code false} otherwise
     * @see org.dk.CommonUtils#capitalize(String)
     */
    boolean capitalize() default false;

    /**
     * Enumeration defining the available case conversion strategies for string normalization.
     * <p>
     * This enum provides options for converting the case of string values during
     * deserialization. The case conversion is applied as the final step in the
     * normalization process.
     *
     * @since 1.0
     */
    enum StringCase {
        /**
         * No case conversion is applied to the string value.
         * <p>
         * The string retains its original case after other normalization
         * operations (trimming, capitalization) have been applied.
         */
        NONE,
        /**
         * Converts the entire string to uppercase.
         * <p>
         * All alphabetic characters in the string are converted to their
         * uppercase equivalents using the default locale.
         *
         * @see String#toUpperCase()
         */
        UPPER,
        /**
         * Converts the entire string to lowercase.
         * <p>
         * All alphabetic characters in the string are converted to their
         * lowercase equivalents using the default locale.
         *
         * @see String#toLowerCase()
         */
        LOWER
    }
}
