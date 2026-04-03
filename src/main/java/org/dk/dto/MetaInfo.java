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

import java.io.Serial;
import java.io.Serializable;

/**
 * Represents metadata information for a single message or error.
 * This structure is used within {@link MetaResponse} to provide detailed feedback.
 *
 * <p>Usage example:
 * <pre>
 * MetaInfo info = new MetaInfo("USER_404", "User not found");
 * </pre>
 *
 * @author Dhineshkumar Dhandapani
 * @version 1.0.5
 * @see org.dk.dto.MetaInfo
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
public class MetaInfo implements Serializable {

    /**
     * Default constructor for MetaInfo.
     */
    public MetaInfo() {}
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * The unique code associated with the metadata/error (e.g., "USER_001").
     * This code can be used for internationalization or programmatic error handling.
     */
    private String code;

    /**
     * The descriptive message associated with the metadata.
     * This message should be human-readable and provide context about the operation or failure.
     */
    private String message;
}
