package org.dk.pdf;

import org.dk.dto.PdfRequestDTO;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PdfServiceTest {

    private final PdfService pdfService = new PdfService();

    @Test
    void testGeneratePdfFromRawBytes() {
        byte[] rawData = "Fake PDF Data".getBytes();
        PdfRequestDTO request = PdfRequestDTO.builder()
                .rawPdfData(rawData)
                .build();

        byte[] result = pdfService.generatePdf(request);
        assertArrayEquals(rawData, result);
    }

    @Test
    void testGeneratePdfFromHtmlFragment() {
        String htmlFragment = "<h1>Hello [[${name}]]</h1><p>This is a test PDF.</p>";
        Map<String, Object> data = new HashMap<>();
        data.put("name", "Dhinesh");

        PdfRequestDTO request = PdfRequestDTO.builder()
                .fileContent(htmlFragment)
                .data(data)
                .build();

        byte[] result = pdfService.generatePdf(request);

        assertNotNull(result);
        assertTrue(result.length > 0);
        // Basic PDF header check
        assertEquals("%PDF", new String(result, 0, 4));
    }

    @Test
    void testGeneratePdfFromFullHtml() {
        String fullHtml = "<!DOCTYPE html><html><body><h1>Title</h1><p th:text=\"${desc}\"></p></body></html>";
        Map<String, Object> data = new HashMap<>();
        data.put("desc", "Dynamic Description");

        PdfRequestDTO request = PdfRequestDTO.builder()
                .fileContent(fullHtml)
                .data(data)
                .build();

        byte[] result = pdfService.generatePdf(request);

        assertNotNull(result);
        assertTrue(result.length > 0);
        assertEquals("%PDF", new String(result, 0, 4));
    }

    @Test
    void testGeneratePdfWithDtoData() {
        String html = "<div>User: <span th:text=\"${name}\"></span></div>";
        TestData testData = new TestData("Test User");

        PdfRequestDTO request = PdfRequestDTO.builder()
                .fileContent(html)
                .data(testData)
                .build();

        byte[] result = pdfService.generatePdf(request);

        assertNotNull(result);
        assertTrue(result.length > 0);
    }

    public record TestData(String name) {
    }
}
