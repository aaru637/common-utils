/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.dk.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dk.CommonUtils;

import java.io.Serial;
import java.io.Serializable;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Represents a collection of metadata information, tracking success/failure status.
 * It provides a thread-safe way to accumulate multiple {@link MetaInfo} objects.
 *
 * <p>Usage example:
 * <pre>
 * MetaResponse meta = new MetaResponse();
 * meta.add(new MetaInfo("INFO_001", "Operation started"), false);
 * </pre>
 *
 * @author Dhineshkumar Dhandapani
 * @version 1.0.5
 * @see org.dk.dto.MetaResponse
 * @since 1.0.5
 * <p>Created at : 2026-02-21</p>
 */
/**
 * Generates getters, setters, toString, equals, and hashCode methods.
 */
@Data
/**
 * Generates a constructor with one parameter for each field in the class.
 */
@AllArgsConstructor
/**
 * Generates a non-argument constructor.
 */
@NoArgsConstructor
public class MetaResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Indicates whether the overall operation was successful.
     */
    private boolean isSuccess = Boolean.TRUE;

    /**
     * A thread-safe list of metadata information objects.
     */
    private CopyOnWriteArrayList<MetaInfo> metaInfos;

    /**
     * Adds a new piece of metadata information and updates the success status.
     *
     * @param metaInfo the metadata information to add
     * @param isError  true if this metadata represents an error, false otherwise
     */
    public void add(MetaInfo metaInfo, boolean isError) {
        if (CommonUtils.isNull(metaInfos)) {
            metaInfos = new CopyOnWriteArrayList<>();
        }
        metaInfos.add(metaInfo);
        resetIsSuccess(isError);
    }

    /**
     * Resets the success status based on whether an error occurred.
     * If an error occurs, the overall success status is set to false.
     * Note: Once a failure is recorded, it persists unless explicitly reset.
     *
     * @param isError true if a failure/error occurred, false for informational messages
     */
    private void resetIsSuccess(boolean isError) {
        if (isError) {
            isSuccess = Boolean.FALSE;
        }
        // If not an error (informational), we do not automatically set isSuccess back to true
        // to avoid masking previous failures in the same response.
    }

    /**
     * Adds all metadata from another MetaResponse and updates the success status.
     *
     * @param metaResponse the MetaResponse to merge from
     */
    public void add(MetaResponse metaResponse) {
        if (CommonUtils.isNull(metaInfos)) {
            metaInfos = new CopyOnWriteArrayList<>();
        }
        if (CommonUtils.isNotNull(metaResponse) && CommonUtils.isNotEmpty(metaResponse.getMetaInfos())) {
            metaInfos.addAll(metaResponse.getMetaInfos());
            resetIsSuccess(!metaResponse.isSuccess());
        }
    }
}
