package pe.edu.vallegrande.vg_ms_grade_management.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.edu.vallegrande.vg_ms_grade_management.domain.enums.EvaluationType;
import pe.edu.vallegrande.vg_ms_grade_management.domain.enums.GradeScale;
import pe.edu.vallegrande.vg_ms_grade_management.domain.enums.TypePeriod;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Modelo de dominio para Grade según estándar MINEDU simplificado v8.0
 * Calificaciones basadas en competencias y capacidades por períodos evaluativos
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Grade {
    private String id;
    
    // Identificación del estudiante y contexto académico (IDs reutilizados de otros microservicios)
    private String studentId;        // ID del estudiante (Student Management)
    private String courseId;         // ID del curso/área curricular (Academic Management)
    private String classroomId;      // ID del aula (Academic Management)
    private String periodId;         // ID del período anual (Academic Management)
    private TypePeriod typePeriod;   // Tipo específico: I_TRIMESTRE, II_BIMESTRE, etc.
    private String teacherId;        // ID del teacher que califica
    
    // Calificación MINEDU - Competencias y Capacidades
    private String competenceName;   // "Resuelve problemas de cantidad"
    private String capacityEvaluated; // "Traduce cantidades a expresiones numéricas"
    
    // Escala de calificación y resultado
    private GradeScale gradeScale;   // AD, A, B, C, INICIO, PROCESO, LOGRADO
    private BigDecimal numericGrade; // Para secundaria: 0-20 (opcional)
    
    // Tipo de evaluación y fecha
    private EvaluationType evaluationType; // FORMATIVA, SUMATIVA, DIAGNOSTICA
    private LocalDate evaluationDate;
    
    // Observaciones del docente
    private String observations;
    
    // Estado y auditoría
    private String status;           // A=Active, M=Modified
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}