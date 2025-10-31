package pe.edu.vallegrande.vg_ms_grade_management.infrastructure.exception;

/**
 * Excepción personalizada para permisos insuficientes
 */
public class InsufficientPermissionsException extends RuntimeException {
    private final String requiredRole;
    private final String action;

    public InsufficientPermissionsException(String requiredRole, String action) {
        super(String.format("Insufficient permissions: '%s' role required to %s", requiredRole, action));
        this.requiredRole = requiredRole;
        this.action = action;
    }

    public String getRequiredRole() {
        return requiredRole;
    }

    public String getAction() {
        return action;
    }
}
