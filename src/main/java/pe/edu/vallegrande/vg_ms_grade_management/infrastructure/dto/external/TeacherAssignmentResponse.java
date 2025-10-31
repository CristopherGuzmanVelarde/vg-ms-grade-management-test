package pe.edu.vallegrande.vg_ms_grade_management.infrastructure.dto.external;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para respuesta del microservicio académico.
 * Representa una asignación de profesor a un aula y curso.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherAssignmentResponse {
    private String id;
    private String teacherId;
    private String classroomId;
    private String courseId;
    private String courseName;
    private String periodId;
    private String typePeriod;
    private String status;
}
