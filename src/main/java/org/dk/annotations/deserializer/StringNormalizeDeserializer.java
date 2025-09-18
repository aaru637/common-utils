package org.dk.annotations.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import org.dk.CommonUtils;
import org.dk.annotations.StringNormalizer;

import java.io.IOException;

/**
 * Custom Jackson deserializer for string normalization operations.
 * <p>
 * This deserializer provides comprehensive string processing capabilities including:
 * <ul>
 *   <li>Trimming whitespace from string values</li>
 *   <li>Converting strings to uppercase or lowercase</li>
 *   <li>Capitalizing the first letter of strings</li>
 *   <li>Handling null and empty string scenarios</li>
 * </ul>
 * <p>
 * The deserializer is configured through the {@link StringNormalizer} annotation
 * and implements {@link ContextualDeserializer} to support field-specific configuration.
 *
 * @author Dhineshkumar Dhandapani
 * @version 1.0
 * @see StringNormalizer
 * @see ContextualDeserializer
 * <p>Created at : 2025-09-17</p>
 */
public class StringNormalizeDeserializer extends JsonDeserializer<String>
        implements ContextualDeserializer {
    /**
     * Flag indicating whether to trim whitespace from string values
     */
    private boolean needsToTrim = true;

    /**
     * Flag indicating whether to return empty string instead of null for null inputs
     */
    private boolean nullIfEmpty = false;

    /**
     * The case conversion strategy to apply to the string
     */
    private StringNormalizer.StringCase caseConversion = StringNormalizer.StringCase.NONE;

    /**
     * Flag indicating whether to capitalize the first letter of the string
     */
    private boolean isCapitalize = false;

    /**
     * Deserializes a JSON string value and applies configured normalization operations.
     * <p>
     * The deserialization process applies operations in the following order:
     * <ol>
     *   <li>Null handling - returns null or empty string based on configuration</li>
     *   <li>Trimming - removes leading and trailing whitespace if enabled</li>
     *   <li>Capitalization - capitalizes first letter if enabled</li>
     *   <li>Case conversion - applies uppercase/lowercase transformation if configured</li>
     * </ol>
     *
     * @param jsonParser the JSON parser containing the string value to deserialize
     * @param context    the deserialization context providing additional information
     * @return the normalized string value, or {@code null} based on configuration
     * @throws IOException if an I/O error occurs during JSON parsing
     */
    @Override
    public String deserialize(JsonParser jsonParser, DeserializationContext context)
            throws IOException {
        String value = jsonParser.getValueAsString();

        if (CommonUtils.isNull(value)) {
            return nullIfEmpty ? CommonUtils.EMPTY_STRING : null;
        }

        if (needsToTrim) {
            value = handleTrim(value);
        }

        if (isCapitalize) {
            value = handleCapitalize(value);
        }

        value = handleCaseConversion(value, caseConversion);

        return value;
    }

    /**
     * Trims leading and trailing whitespace from the given string.
     *
     * @param value the string to trim, must not be {@code null}
     * @return the trimmed string with leading and trailing whitespace removed
     */
    private String handleTrim(String value) {
        return value.trim();
    }

    /**
     * Converts the string to the specified case format.
     * <p>
     * Supports the following case conversions:
     * <ul>
     *   <li>{@link StringNormalizer.StringCase#UPPER} - converts to uppercase</li>
     *   <li>{@link StringNormalizer.StringCase#LOWER} - converts to lowercase</li>
     *   <li>{@link StringNormalizer.StringCase#NONE} - no case conversion (default)</li>
     * </ul>
     *
     * @param value          the string to convert, must not be {@code null}
     * @param caseConversion the case conversion strategy to apply
     * @return the string converted to the specified case, or the original string if no conversion is specified
     */
    private String handleCaseConversion(String value, StringNormalizer.StringCase caseConversion) {
        switch (caseConversion) {
            case StringNormalizer.StringCase.UPPER -> {
                return value.toUpperCase();
            }
            case StringNormalizer.StringCase.LOWER -> {
                return value.toLowerCase();
            }
            default -> {
                return value;
            }
        }
    }

    /**
     * Capitalizes the first letter of the given string.
     * <p>
     * Uses {@link CommonUtils#capitalize(String)} to perform the capitalization operation.
     * The exact behavior depends on the implementation of the utility method.
     *
     * @param value the string to capitalize, must not be {@code null}
     * @return the string with its first letter capitalized
     * @see CommonUtils#capitalize(String)
     */
    private String handleCapitalize(String value) {
        return CommonUtils.capitalize(value);
    }

    /**
     * Creates a contextual deserializer based on the {@link StringNormalizer} annotation configuration.
     * <p>
     * This method is called by Jackson during deserialization setup to create a deserializer
     * instance configured with the specific settings from the {@code @StringNormalizer} annotation
     * present on the target field or property.
     * <p>
     * <strong>Note:</strong> There appears to be a bug in the current implementation where
     * annotation values are not being properly extracted and applied to the new deserializer instance.
     *
     * @param context  the deserialization context providing access to configuration and type information
     * @param property the bean property being deserialized, containing annotation metadata
     * @return a new contextual deserializer configured with annotation settings, or {@code this} if no annotation is present
     * @throws JsonMappingException if an error occurs during contextual deserializer creation
     * @see ContextualDeserializer#createContextual(DeserializationContext, BeanProperty)
     */
    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext context,
                                                BeanProperty property) throws JsonMappingException {
        StringNormalizer annotation = property.getAnnotation(StringNormalizer.class);
        if (annotation != null) {
            StringNormalizeDeserializer deserializer = new StringNormalizeDeserializer();
            deserializer.needsToTrim = annotation.trim();
            deserializer.nullIfEmpty = annotation.nullIfEmpty();
            deserializer.caseConversion = annotation.caseConversion();
            deserializer.isCapitalize = annotation.capitalize();
            return deserializer;
        }

        return this;
    }
}
