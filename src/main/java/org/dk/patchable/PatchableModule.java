package org.dk.patchable;

import com.fasterxml.jackson.databind.module.SimpleModule;

/**
 * Jackson {@link com.fasterxml.jackson.databind.Module} that enables automatic
 * tracking of {@link Patchable @Patchable} fields
 * during JSON deserialization.
 *
 * <h3>Registration:</h3>
 * <pre>{@code
 * ObjectMapper mapper = new ObjectMapper();
 * mapper.registerModule(new PatchableModule());
 * }</pre>
 *
 * <h3>Spring Boot auto-configuration:</h3>
 * <pre>{@code
 * @Configuration
 * public class JacksonConfig {
 *     @Bean
 *     public PatchableModule patchableModule() {
 *         return new PatchableModule();
 *     }
 * }
 * }</pre>
 *
 * <p>Once registered, any class annotated with {@code @Patchable} (at class or field level)
 * will automatically have its deserialized instances enriched with a
 * {@link PatchContext} that tracks which fields were present
 * vs. absent in the JSON input.</p>
 *
 * @author Dhineshkumar Dhandapani
 * @version 2.0.3
 * <p>Created at : 2026-04-03</p>
 * @see Patchable
 * @see PatchContext
 * @see PatchableAware
 * @see PatchContextHolder
 */
public class PatchableModule extends SimpleModule {

    private static final String MODULE_NAME = "PatchableModule";

    public PatchableModule() {
        super(MODULE_NAME);
    }

    @Override
    public void setupModule(SetupContext context) {
        super.setupModule(context);
        context.addBeanDeserializerModifier(new PatchableDeserializerModifier());
    }
}
