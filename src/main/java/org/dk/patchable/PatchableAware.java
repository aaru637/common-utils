package org.dk.patchable;

/**
 * Optional interface that DTOs can implement to carry their own {@link PatchContext}.
 *
 * <p>When a DTO implements this interface, the deserialization integration (e.g., Jackson module)
 * will automatically attach the {@code PatchContext} directly to the object. This provides
 * a clean, self-contained API without needing external lookups.</p>
 *
 * <h2>Example:</h2>
 * <pre>{@code
 * @Patchable
 * public class UserUpdateRequest implements PatchableAware {
 *     private String name;
 *     private String email;
 *
 *     private transient PatchContext patchContext;
 *
 *     @Override
 *     public PatchContext getPatchContext() {
 *         return patchContext;
 *     }
 *
 *     @Override
 *     public void setPatchContext(PatchContext patchContext) {
 *         this.patchContext = patchContext;
 *     }
 * }
 * }</pre>
 *
 * <p>If your DTO cannot implement this interface (e.g., third-party classes), use the
 * {@code PatchContextHolder} from the Jackson module instead.</p>
 *
 * @author Dhineshkumar Dhandapani
 * @version 2.0.3
 * <p>Created at : 2026-04-03</p>
 * @see PatchContext
 */
public interface PatchableAware {

    /**
     * Returns the {@link PatchContext} associated with this object.
     *
     * @return the patch context, or {@code null} if not yet set
     */
    PatchContext getPatchContext();

    /**
     * Sets the {@link PatchContext} for this object.
     * This is typically called by the deserialization integration layer.
     *
     * @param patchContext the patch context to associate
     */
    void setPatchContext(PatchContext patchContext);
}

