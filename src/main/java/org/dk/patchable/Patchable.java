package org.dk.patchable;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Mandatory config for spring boot project:
 * <pre>{@code
 * package com.zenrocks.milk_360.config;
 *
 * import com.fasterxml.jackson.databind.DeserializationFeature;
 * import com.fasterxml.jackson.databind.MapperFeature;
 * import com.fasterxml.jackson.databind.ObjectMapper;
 * import com.fasterxml.jackson.databind.json.JsonMapper;
 * import jakarta.annotation.Nullable;
 * import org.dk.patchable.Patchable;
 * import org.dk.patchable.PatchableFieldTracker;
 * import org.dk.patchable.PatchableModule;
 * import org.jspecify.annotations.NonNull;
 * import org.springframework.beans.factory.ObjectProvider;
 * import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
 * import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
 * import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
 * import org.springframework.context.annotation.Bean;
 * import org.springframework.context.annotation.Configuration;
 * import org.springframework.core.ResolvableType;
 * import org.springframework.http.HttpInputMessage;
 * import org.springframework.http.HttpOutputMessage;
 * import org.springframework.http.MediaType;
 * import org.springframework.http.converter.AbstractGenericHttpMessageConverter;
 * import org.springframework.http.converter.HttpMessageConverters;
 * import org.springframework.http.converter.HttpMessageNotReadableException;
 * import org.springframework.http.converter.HttpMessageNotWritableException;
 * import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
 *
 * import java.io.IOException;
 * import java.lang.reflect.Type;
 *
 * /**
 *  * @author Dhineshkumar Dhandapani
 *  * @version 2.0.3
 *  * <p>Created at : 2026-04-03</p>
 *  *&#47;
 * @Configuration(proxyBeanMethods = false)
 * @ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
 * @ConditionalOnClass(ObjectMapper.class)
 * public class PatchableWebMvcAutoConfiguration {
 *
 *     @Bean
 *     @ConditionalOnMissingBean
 *     public PatchableModule patchableModule() {
 *         return new PatchableModule();
 *     }
 *
 *     @Bean
 *     @ConditionalOnMissingBean
 *     public PatchableRequestBodyConverter patchableRequestBodyConverter(
 *             ObjectProvider<ObjectMapper> objectMapperProvider,
 *             PatchableModule patchableModule) {
 *         ObjectMapper patchableObjectMapper = objectMapperProvider.getIfAvailable();
 *
 *         if (patchableObjectMapper != null) {
 *             patchableObjectMapper = patchableObjectMapper.copy();
 *         } else {
 *             patchableObjectMapper = JsonMapper.builder().findAndAddModules().disable(MapperFeature.DEFAULT_VIEW_INCLUSION).disable(
 *                     DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES).build();
 *         }
 *
 *         Object patchableModuleId = patchableModule.getTypeId();
 *         if (patchableModuleId == null || !patchableObjectMapper.getRegisteredModuleIds().contains(patchableModuleId)) {
 *             patchableObjectMapper.registerModule(patchableModule);
 *         }
 *
 *         return new PatchableRequestBodyConverter(patchableObjectMapper);
 *     }
 *
 *     @Bean
 *     public WebMvcConfigurer patchableWebMvcConfigurer(PatchableRequestBodyConverter patchableRequestBodyConverter) {
 *         return new WebMvcConfigurer() {
 *             @Override
 *             public void configureMessageConverters(HttpMessageConverters.@NonNull ServerBuilder builder) {
 *                 builder.addCustomConverter(patchableRequestBodyConverter);
 *             }
 *         };
 *     }
 *
 *     public static class PatchableRequestBodyConverter extends AbstractGenericHttpMessageConverter<Object> {
 *         private final ObjectMapper objectMapper;
 *
 *         PatchableRequestBodyConverter(ObjectMapper patchableObjectMapper) {
 *             super(MediaType.APPLICATION_JSON, new MediaType("application", "*+json"));
 *             this.objectMapper = patchableObjectMapper;
 *         }
 *
 *         @Override
 *         protected boolean supports(@NonNull Class<?> clazz) {
 *             return isPatchableType(clazz);
 *         }
 *
 *         @Override
 *         public boolean canRead(@NonNull Type type, @Nullable Class<?> contextClass, @Nullable MediaType mediaType) {
 *             Class<?> targetClass = ResolvableType.forType(type).resolve();
 *             return targetClass != null && isPatchableType(targetClass) && canRead(mediaType);
 *         }
 *
 *         @Override
 *         public Object read(@NonNull Type type, @Nullable Class<?> contextClass, HttpInputMessage inputMessage) throws
 *                 HttpMessageNotReadableException {
 *             try {
 *                 return objectMapper.readValue(inputMessage.getBody(), objectMapper.constructType(type));
 *             } catch (IOException ex) {
 *                 throw new HttpMessageNotReadableException("Failed to read patchable request body", ex, inputMessage);
 *             }
 *         }
 *
 *         @Override
 *         public boolean canWrite(@NonNull Class<?> clazz, @Nullable MediaType mediaType) {
 *             return false;
 *         }
 *
 *         @Override
 *         public boolean canWrite(Type type, @NonNull Class<?> clazz, @Nullable MediaType mediaType) {
 *             return false;
 *         }
 *
 *         @Override
 *         protected Object readInternal(@NonNull Class<?> clazz, HttpInputMessage inputMessage) throws HttpMessageNotReadableException {
 *             try {
 *                 return objectMapper.readValue(inputMessage.getBody(), clazz);
 *             } catch (IOException ex) {
 *                 throw new HttpMessageNotReadableException("Failed to read patchable request body", ex, inputMessage);
 *             }
 *         }
 *
 *         @Override
 *         protected void writeInternal(@NonNull Object object, Type type, @NonNull HttpOutputMessage outputMessage) throws
 *                 HttpMessageNotWritableException {
 *             throw new HttpMessageNotWritableException("PatchableRequestBodyConverter does not support writing");
 *         }
 *
 *         @Override
 *         protected void writeInternal(@NonNull Object object, @NonNull HttpOutputMessage outputMessage) throws HttpMessageNotWritableException {
 *             throw new HttpMessageNotWritableException("PatchableRequestBodyConverter does not support writing");
 *         }
 *
 *         private boolean isPatchableType(Class<?> targetClass) {
 *             return targetClass.isAnnotationPresent(Patchable.class) || !PatchableFieldTracker.getPatchableFields(targetClass).isEmpty();
 *         }
 *     }
 * }
 * }</pre>
 *
 * <p>Marks a class or individual fields as patchable for partial update tracking.</p>
 *
 * <p><b>Class-level usage:</b> All fields in the class are automatically tracked.</p>
 * <pre>{@code
 * @Patchable
 * public class UserUpdateRequest {
 *     private String name;
 *     private String email;
 * }
 * }</pre>
 *
 * <p><b>Field-level usage:</b> Only annotated fields are tracked.</p>
 * <pre>{@code
 * public class UserUpdateRequest {
 *     @Patchable
 *     private String name;
 *
 *     @Patchable(name = "phone_number")
 *     private String phoneNumber;
 *
 *     private String internalField; // not tracked
 * }
 * }</pre>
 *
 * @see PatchContext
 * @see PatchableFieldTracker
 */
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Patchable {

    /**
     * Optional alias for the field name used in the tracking map.
     * Defaults to the Java field name if not specified.
     *
     * <p>This is only meaningful when applied at the field level.
     * At the class level, this attribute is ignored.</p>
     *
     * @return the custom field name, or empty string to use the Java field name
     */
    String name() default "";
}
