package pe.edu.vallegrande.vg_ms_grade_management.infrastructure.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import pe.edu.vallegrande.vg_ms_grade_management.domain.enums.EvaluationType;
import pe.edu.vallegrande.vg_ms_grade_management.domain.enums.GradeScale;
import pe.edu.vallegrande.vg_ms_grade_management.domain.enums.TypePeriod;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Documento MongoDB para Grade según estándar MINEDU simplificado v8.0
 * Tabla única: student_grades
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "student_grades")
public class GradeDocument {
    @Id
    private String id;
    
    // Identificación del estudiante y contexto académico (IDs sin foreign keys)
    private String studentId;        // Estudiante calificado
    private String courseId;         // Curso/área curricular (REUTILIZA Academic Management)
    private String classroomId;      // Aula donde se evalúa (REUTILIZA Academic Management)
    private String periodId;         // Período evaluativo anual (REUTILIZA Academic Management)
    private TypePeriod typePeriod;   // Tipo específico de período
    private String teacherId;        // Teacher que califica
    
    // Calificación MINEDU
    private String competenceName;   // "Resuelve problemas de cantidad"
    private String capacityEvaluated; // "Traduce cantidades a expresiones numéricas"
    
    // Escala de calificación
    private GradeScale gradeScale;   // AD, A, B, C, INICIO, PROCESO, LOGRADO
    private BigDecimal numericGrade; // Para secundaria: 0-20
    
    // Tipo de evaluación
    private EvaluationType evaluationType; // FORMATIVA, SUMATIVA, DIAGNOSTICA
    private LocalDate evaluationDate;
    
    // Observaciones
    private String observations;
    
    // Estado y auditoría
    private String status;           // A=Active, M=Modified
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}