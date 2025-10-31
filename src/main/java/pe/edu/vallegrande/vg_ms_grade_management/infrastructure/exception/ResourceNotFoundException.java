package pe.edu.vallegrande.vg_ms_grade_management.infrastructure.exception;

/**
 * Excepción personalizada para recursos no encontrados
 */
public class ResourceNotFoundException extends RuntimeException {
    private final String resourceName;
    private final String resourceId;

    public ResourceNotFoundException(String resourceName, String resourceId) {
        super(String.format("%s with id '%s' not found", resourceName, resourceId));
        this.resourceName = resourceName;
        this.resourceId = resourceId;
    }

    public String getResourceName() {
        return resourceName;
    }

    public String getResourceId() {
        return resourceId;
    }
}
