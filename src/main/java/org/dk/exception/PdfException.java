package org.dk.exception;

/**
 * Exception thrown when PDF generation fails.
 *
 * @author Dhineshkumar Dhandapani
 * @version 1.0
 * @since 1.0
 */
public class PdfException extends RuntimeException {

    public PdfException(String message) {
        super(message);
    }

    public PdfException(String message, Throwable cause) {
        super(message, cause);
    }
}
