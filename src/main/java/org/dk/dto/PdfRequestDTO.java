package org.dk.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for PDF generation requests.
 *
 * @author Dhineshkumar Dhandapani
 * @version 1.0
 * @since 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PdfRequestDTO {

    /**
     * The desired name for the generated PDF file.
     */
    private String fileName;

    /**
     * The HTML content to be converted to PDF.
     * Can contain Thymeleaf placeholders.
     */
    private String fileContent;

    /**
     * The data object (DTO or Map) used to populate placeholders in the HTML content.
     */
    private Object data;

    /**
     * Raw PDF data as byte array. If provided, this may be returned directly
     * or used as the basis for further processing.
     */
    private byte[] rawPdfData;
}
