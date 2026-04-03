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
 * A generic container for API responses, designed to provide a consistent structure across services.
 * It encapsulates the HTTP-like status code, the actual data payload, and detailed metadata.
 *
 * <p>Usage example:
 * <pre>
 * ApiResponse&lt;User&gt; response = new ApiResponse&lt;&gt;();
 * response.setStatus(200);
 * response.setData(userObject);
 * </pre>
 *
 * @param <T> the type of the data payload being returned
 * @author Dhineshkumar Dhandapani
 * @version 1.0.5
 * @see org.dk.dto.ApiResponse
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
public class ApiResponse implements Serializable {
    
    /**
     * Default constructor for ApiResponse.
     */
    public ApiResponse() {}
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * The HTTP-like status code of the response (e.g., 200 for OK, 400 for Bad Request).
     * Defaults to 400 (Bad Request) to encourage explicit status setting.
     */
    private Integer status = 400;

    /**
     * The data payload of the response. This field contains the actual result of the API call.
     */
    private Object data;

    /**
     * The metadata containing additional information, warnings, or errors associated with the request.
     */
    private MetaResponse metaResponse;
}
