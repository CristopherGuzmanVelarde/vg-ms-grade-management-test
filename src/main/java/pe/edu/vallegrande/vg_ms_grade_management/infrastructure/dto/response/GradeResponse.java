package pe.edu.vallegrande.vg_ms_grade_management.infrastructure.dto.response;

import lombok.Data;
import pe.edu.vallegrande.vg_ms_grade_management.domain.enums.EvaluationType;
import pe.edu.vallegrande.vg_ms_grade_management.domain.enums.GradeScale;
import pe.edu.vallegrande.vg_ms_grade_management.domain.enums.TypePeriod;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO de respuesta para calificaciones según estándar MINEDU v8.0
 */
@Data
public class GradeResponse {
    private String id;
    
    // Identificación del estudiante y contexto académico
    private String studentId;
    private String courseId;
    private String classroomId;
    private String periodId;
    private TypePeriod typePeriod;
    private String teacherId;
    
    // Calificación MINEDU
    private String competenceName;
    private String capacityEvaluated;
    
    // Escala de calificación y resultado
    private GradeScale gradeScale;
    private BigDecimal numericGrade;
    
    // Tipo de evaluación y fecha
    private EvaluationType evaluationType;
    private LocalDate evaluationDate;
    
    // Observaciones
    private String observations;
    
    // Estado y auditoría
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}