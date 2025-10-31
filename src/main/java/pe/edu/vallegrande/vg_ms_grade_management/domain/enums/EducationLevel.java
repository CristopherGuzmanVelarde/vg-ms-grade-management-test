package pe.edu.vallegrande.vg_ms_grade_management.domain.enums;

/**
 * Enumeración para los niveles educativos según el sistema educativo peruano
 */
public enum EducationLevel {
    INICIAL("Inicial"),
    PRIMARIA("Primaria"),
    SECUNDARIA("Secundaria");
    
    private final String description;
    
    EducationLevel(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
}