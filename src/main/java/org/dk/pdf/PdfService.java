package org.dk.pdf;

import org.dk.dto.PdfRequestDTO;
import org.dk.exception.PdfException;
import org.dk.CommonUtils;
import org.dk.FileOperations;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.StringTemplateResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.regex.Pattern;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Service for generating PDF documents from various sources.
 * Supports raw bytes, HTML strings, and Thymeleaf templates.
 *
 * @author Dhineshkumar Dhandapani
 * @version 2.0.1
 * @since 2.0.1
 */
public class PdfService {

    private final TemplateEngine templateEngine;
    private final ObjectMapper objectMapper;

    /**
     * Default constructor for PdfService.
     * Initializes the template engine and object mapper.
     */
    public PdfService() {
        this.templateEngine = createTemplateEngine();
        this.objectMapper = new ObjectMapper();
    }

    private TemplateEngine createTemplateEngine() {
        TemplateEngine engine = new TemplateEngine();
        StringTemplateResolver resolver = new StringTemplateResolver();
        resolver.setTemplateMode(TemplateMode.HTML);
        engine.setTemplateResolver(resolver);
        return engine;
    }

    /**
     * Generates a PDF based on the provided request.
     *
     * @param request the PDF generation request
     * @return the generated PDF as a byte array
     * @throws PdfException if generation fails
     */
    public byte[] generatePdf(PdfRequestDTO request) {
        if (request == null) {
            throw new PdfException("PdfRequestDTO must not be null");
        }

        // 1. Check for raw PDF data
        if (request.getRawPdfData() != null && request.getRawPdfData().length > 0) {
            return request.getRawPdfData();
        }

        // 2. Process HTML content if present
        if (CommonUtils.isNotEmpty(request.getFileContent())) {
            String processedHtml = processTemplate(request.getFileContent(), request.getData());
            String xhtml = ensureXhtml(processedHtml);
            return convertHtmlToPdf(xhtml);
        }

        throw new PdfException("Request contains no valid data for PDF generation (rawPdfData or fileContent)");
    }

    private String processTemplate(String content, Object data) {
        Context context = new Context();
        if (data != null) {
            try {
                @SuppressWarnings("unchecked")
                Map<String, Object> map = objectMapper.convertValue(data, Map.class);
                context.setVariables(map);
            } catch (Exception e) {
                // Fallback or simple mapping if Object is a Map already
                if (data instanceof Map) {
                    context.setVariables((Map<String, Object>) data);
                } else {
                    throw new PdfException("Failed to map data to template context", e);
                }
            }
        }
        return templateEngine.process(content, context);
    }

    private String ensureXhtml(String html) {
        html = html.trim();
        // Simple wrap if it's just a fragment (e.g. from TinyMCE)
        if (!Pattern.compile("(?i)<html").matcher(html).find()) {
            return "<!DOCTYPE html><html><head><meta charset=\"UTF-8\"/><style>body { font-family: 'Arial', sans-serif; }</style></head><body>" 
                   + html + "</body></html>";
        }
        // Basic check/fix for common non-XHTML issues if needed could be added here
        // For now, assume it's relatively clean or managed by the user
        return html;
    }

    private byte[] convertHtmlToPdf(String xhtml) {
        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            ITextRenderer renderer = new iTextRendererWrapper();
            renderer.setDocumentFromString(xhtml);
            renderer.layout();
            renderer.createPDF(os);
            return os.toByteArray();
        } catch (Exception e) {
            throw new PdfException("Error occurred during HTML to PDF conversion", e);
        }
    }

    /**
     * Saves the PDF byte array to a file at the specified path.
     *
     * @param pdfData  the PDF byte array
     * @param filePath the path to save the PDF file
     * @return the saved File object
     * @throws PdfException if saving fails
     */
    public java.io.File savePdfToFile(byte[] pdfData, String filePath) {
        try {
            return FileOperations.writeByteToFile(filePath, pdfData);
        } catch (java.io.IOException e) {
            throw new PdfException("Failed to save PDF to file: " + filePath, e);
        }
    }

    /**
     * Internal wrapper to handle potential class loading or initialization issues
     * with Flying Saucer in some environments.
     */
    private static class iTextRendererWrapper extends ITextRenderer {
        // Use default configuration
    }
}
