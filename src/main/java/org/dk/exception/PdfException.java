package org.dk.exception;

/**
 * Exception thrown when PDF generation fails.
 *
 * @author Dhineshkumar Dhandapani
 * @version 1.0
 * @since 1.0
 */
public class PdfException extends RuntimeException {

    /**
     * Constructs a new PdfException with the specified detail message.
     *
     * @param message the detail message
     */
    public PdfException(String message) {
        super(message);
    }

    /**
     * Constructs a new PdfException with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause   the cause
     */
    public PdfException(String message, Throwable cause) {
        super(message, cause);
    }
}
