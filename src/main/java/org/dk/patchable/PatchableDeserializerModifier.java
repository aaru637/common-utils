package org.dk.patchable;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.BeanDeserializer;
import com.fasterxml.jackson.databind.deser.BeanDeserializerBase;
import com.fasterxml.jackson.databind.deser.BeanDeserializerModifier;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import com.fasterxml.jackson.databind.util.TokenBuffer;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Jackson {@link BeanDeserializerModifier} that intercepts deserialization of
 * {@link Patchable @Patchable} annotated classes to track which JSON fields
 * were present in the input.
 *
 * <p>
 * This modifier wraps the default Jackson deserializer with a custom one that:
 * </p>
 * <ol>
 * <li>Creates a {@link PatchContext} with all patchable fields marked as
 * ABSENT</li>
 * <li>Delegates to the default deserializer for actual field binding</li>
 * <li>Tracks which JSON properties appear during parsing and marks them
 * PRESENT</li>
 * <li>Attaches the PatchContext to the deserialized object</li>
 * </ol>
 *
 * @author Dhineshkumar Dhandapani
 * @version 2.0.3
 * <p>Created at : 2026-04-03</p>
 */
class PatchableDeserializerModifier extends BeanDeserializerModifier {

    @Override
    public JsonDeserializer<?> modifyDeserializer(DeserializationConfig config, BeanDescription beanDesc, JsonDeserializer<?> deserializer) {

        Class<?> beanClass = beanDesc.getBeanClass();

        // Only wrap if the class (or any of its fields) uses @Patchable
        boolean isPatchable = beanClass.isAnnotationPresent(Patchable.class) || !PatchableFieldTracker.getPatchableFields(beanClass).isEmpty();

        if (isPatchable && deserializer instanceof BeanDeserializerBase baseDeserializer) {
            return new PatchableTrackingDeserializer(baseDeserializer, beanDesc);
        }

        return deserializer;
    }

    /**
     * Custom deserializer that wraps the default Jackson bean deserializer to track
     * which JSON fields were present during deserialization.
     */
    private static class PatchableTrackingDeserializer extends BeanDeserializer {

        private final Class<?> beanClass;
        private final Map<String, String> bindingNameMapping;
        private final Map<String, String> trackingNameMapping;

        protected PatchableTrackingDeserializer(BeanDeserializerBase source, BeanDescription beanDesc) {
            super(source);
            this.beanClass = beanDesc.getBeanClass();

            FieldMappings fieldMappings = buildFieldMappings(beanDesc);
            this.bindingNameMapping = fieldMappings.bindingNameMapping();
            this.trackingNameMapping = fieldMappings.trackingNameMapping();
        }

        @Override
        public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            PatchContext patchContext = PatchableFieldTracker.createContext(beanClass);
            JsonToken currentToken = p.currentToken();

            if (currentToken == null) {
                currentToken = p.nextToken();
            }

            if (currentToken == JsonToken.START_OBJECT || currentToken == JsonToken.FIELD_NAME) {
                Object bean = deserializeTrackedObject(p, ctxt, patchContext, currentToken);
                attachContext(bean, patchContext);
                return bean;
            }

            Object bean = super.deserialize(p, ctxt);
            attachContext(bean, patchContext);
            return bean;
        }

        private Object deserializeTrackedObject(JsonParser p, DeserializationContext ctxt, PatchContext patchContext, JsonToken currentToken) throws
                IOException {

            TokenBuffer buffer = new TokenBuffer(p);
            buffer.writeStartObject();

            if (currentToken == JsonToken.START_OBJECT) {
                currentToken = p.nextToken();
            }

            while (currentToken == JsonToken.FIELD_NAME) {
                String incomingFieldName = p.currentName();
                markPresent(incomingFieldName, patchContext);
                buffer.writeFieldName(bindingNameMapping.getOrDefault(incomingFieldName, incomingFieldName));

                p.nextToken();
                buffer.copyCurrentStructure(p);
                currentToken = p.nextToken();
            }

            buffer.writeEndObject();

            JsonParser bufferedParser = buffer.asParser(p);
            bufferedParser.nextToken();
            return super.deserialize(bufferedParser, ctxt);
        }

        private void markPresent(String incomingFieldName, PatchContext patchContext) {
            String resolvedName = trackingNameMapping.get(incomingFieldName);
            if (resolvedName != null) {
                patchContext.markPresent(resolvedName);
            }
        }

        private FieldMappings buildFieldMappings(BeanDescription beanDesc) {
            List<PatchableFieldTracker.PatchableFieldInfo> patchableFields = PatchableFieldTracker.getPatchableFieldInfos(beanClass);

            Map<String, BeanPropertyDefinition> propertiesByInternalName = new LinkedHashMap<>();
            for (BeanPropertyDefinition property : beanDesc.findProperties()) {
                propertiesByInternalName.putIfAbsent(property.getInternalName(), property);
            }

            Map<String, String> bindingNames = new LinkedHashMap<>();
            Map<String, String> trackingNames = new LinkedHashMap<>();

            for (PatchableFieldTracker.PatchableFieldInfo info : patchableFields) {
                String internalName = info.field().getName();
                BeanPropertyDefinition property = propertiesByInternalName.get(internalName);
                String jacksonPropertyName = property != null ? property.getName() : internalName;

                registerFieldName(bindingNames, trackingNames, jacksonPropertyName, jacksonPropertyName, info.resolvedName());
                registerFieldName(bindingNames, trackingNames, internalName, jacksonPropertyName, info.resolvedName());
                registerFieldName(bindingNames, trackingNames, info.resolvedName(), jacksonPropertyName, info.resolvedName());
            }

            return new FieldMappings(bindingNames, trackingNames);
        }

        private void registerFieldName(
                Map<String, String> bindingNames, Map<String, String> trackingNames, String incomingName,
                String jacksonPropertyName, String resolvedName) {

            bindingNames.putIfAbsent(incomingName, jacksonPropertyName);
            trackingNames.putIfAbsent(incomingName, resolvedName);
        }

        /**
         * Attaches the PatchContext to the deserialized bean.
         * If the bean implements PatchableAware, sets it directly.
         * Otherwise, stores it in PatchContextHolder.
         */
        private void attachContext(Object bean, PatchContext patchContext) {
            if (bean instanceof PatchableAware aware) {
                aware.setPatchContext(patchContext);
            } else {
                PatchContextHolder.set(bean, patchContext);
            }
        }

        private record FieldMappings(Map<String, String> bindingNameMapping, Map<String, String> trackingNameMapping) {}
    }
}
