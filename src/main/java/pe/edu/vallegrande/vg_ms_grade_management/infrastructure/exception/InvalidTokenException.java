package pe.edu.vallegrande.vg_ms_grade_management.infrastructure.exception;

/**
 * Excepción personalizada para tokens inválidos o headers mal formados
 */
public class InvalidTokenException extends RuntimeException {
    private final String headerName;
    private final String reason;

    public InvalidTokenException(String headerName, String reason) {
        super(String.format("Invalid header '%s': %s", headerName, reason));
        this.headerName = headerName;
        this.reason = reason;
    }

    public String getHeaderName() {
        return headerName;
    }

    public String getReason() {
        return reason;
    }
}
