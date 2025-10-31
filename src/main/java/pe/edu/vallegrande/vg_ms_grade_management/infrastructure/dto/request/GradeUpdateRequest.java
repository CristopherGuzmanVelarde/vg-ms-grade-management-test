package pe.edu.vallegrande.vg_ms_grade_management.infrastructure.dto.request;

import lombok.Data;
import pe.edu.vallegrande.vg_ms_grade_management.domain.enums.GradeScale;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * DTO para actualizar calificaciones - Solo campos editables
 * Los IDs de referencia (studentId, courseId, etc.) NO se pueden modificar
 */
@Data
public class GradeUpdateRequest {
    // Calificación MINEDU (editables)
    @NotBlank(message = "El nombre de la competencia es requerido")
    private String competenceName;
    
    @NotBlank(message = "La capacidad evaluada es requerida")
    private String capacityEvaluated;
    
    @NotNull(message = "La escala de calificación es requerida")
    private GradeScale gradeScale;
    
    // Opcional para secundaria (escala 0-20)
    private Double numericGrade;
    
    // Tipo de evaluación (requerido como String para flexibilidad)
    @NotNull(message = "El tipo de evaluación es requerido")
    private String evaluationType;
    
    @NotNull(message = "La fecha de evaluación es requerida")
    private String evaluationDate;
    
    // Observaciones opcionales
    private String observations;
}
