package org.dk.annotations.deserializer;

import org.dk.CommonUtils;
import org.dk.annotations.StringNormalizer;
import tools.jackson.core.JsonParser;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.BeanProperty;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.ValueDeserializer;

/**
 * Jackson 3 variant of {@link StringNormalizeDeserializer}.
 */
public class StringNormalizeValueDeserializer extends ValueDeserializer<String> {

    private final boolean needsToTrim;
    private final boolean nullIfEmpty;
    private final StringNormalizer.StringCase caseConversion;
    private final boolean capitalize;

    public StringNormalizeValueDeserializer() {
        this(true, false, StringNormalizer.StringCase.NONE, false);
    }

    private StringNormalizeValueDeserializer(
            boolean needsToTrim,
            boolean nullIfEmpty,
            StringNormalizer.StringCase caseConversion,
            boolean capitalize) {
        this.needsToTrim = needsToTrim;
        this.nullIfEmpty = nullIfEmpty;
        this.caseConversion = caseConversion;
        this.capitalize = capitalize;
    }

    @Override
    public String deserialize(JsonParser jsonParser, DeserializationContext context) throws JacksonException {
        String value = jsonParser.getValueAsString();

        if (CommonUtils.isNull(value)) {
            return nullIfEmpty ? CommonUtils.EMPTY_STRING : null;
        }

        if (needsToTrim) {
            value = value.trim();
        }

        if (capitalize) {
            value = CommonUtils.capitalize(value);
        }

        return switch (caseConversion) {
            case UPPER -> value.toUpperCase();
            case LOWER -> value.toLowerCase();
            default -> value;
        };
    }

    @Override
    public ValueDeserializer<?> createContextual(DeserializationContext context, BeanProperty property) {
        if (property == null) {
            return this;
        }

        StringNormalizer annotation = property.getAnnotation(StringNormalizer.class);
        if (annotation == null) {
            return this;
        }

        return new StringNormalizeValueDeserializer(
                annotation.trim(),
                annotation.nullIfEmpty(),
                annotation.caseConversion(),
                annotation.capitalize());
    }
}
