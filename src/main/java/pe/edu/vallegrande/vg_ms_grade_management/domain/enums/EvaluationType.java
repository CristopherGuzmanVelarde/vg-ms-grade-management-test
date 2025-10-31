package pe.edu.vallegrande.vg_ms_grade_management.domain.enums;

/**
 * Enumeración para los tipos de evaluación según el sistema educativo peruano
 * 
 * FORMATIVA: Evaluación continua durante el proceso de aprendizaje
 * SUMATIVA: Evaluación al final de un período o unidad
 * DIAGNOSTICA: Evaluación inicial para conocer saberes previos
 */
public enum EvaluationType {
    FORMATIVA("Formativa"),
    SUMATIVA("Sumativa"),
    DIAGNOSTICA("Diagnóstica");
    
    private final String description;
    
    EvaluationType(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
}