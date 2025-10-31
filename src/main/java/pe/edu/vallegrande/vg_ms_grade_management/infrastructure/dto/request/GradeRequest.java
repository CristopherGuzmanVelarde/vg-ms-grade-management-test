package pe.edu.vallegrande.vg_ms_grade_management.infrastructure.dto.request;

import lombok.Data;
import pe.edu.vallegrande.vg_ms_grade_management.domain.enums.EvaluationType;
import pe.edu.vallegrande.vg_ms_grade_management.domain.enums.GradeScale;
import pe.edu.vallegrande.vg_ms_grade_management.domain.enums.TypePeriod;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO para registrar calificaciones según estándar MINEDU v8.0
 */
@Data
public class GradeRequest {
    // Identificación (requeridos)
    @NotBlank(message = "El ID del estudiante es requerido")
    private String studentId;
    
    @NotBlank(message = "El ID del curso es requerido")
    private String courseId;
    
    @NotBlank(message = "El ID del aula es requerido")
    private String classroomId;
    
    @NotBlank(message = "El ID del período es requerido")
    private String periodId;
    
    @NotNull(message = "El tipo de período es requerido")
    private TypePeriod typePeriod;
    
    // Calificación MINEDU (requeridos)
    @NotBlank(message = "El nombre de la competencia es requerido")
    private String competenceName;
    
    @NotBlank(message = "La capacidad evaluada es requerida")
    private String capacityEvaluated;
    
    @NotNull(message = "La escala de calificación es requerida")
    private GradeScale gradeScale;
    
    // Opcional para secundaria (escala 0-20)
    private BigDecimal numericGrade;
    
    // Tipo de evaluación (requerido)
    @NotNull(message = "El tipo de evaluación es requerido")
    private EvaluationType evaluationType;
    
    @NotNull(message = "La fecha de evaluación es requerida")
    private LocalDate evaluationDate;
    
    // Observaciones opcionales
    private String observations;
}