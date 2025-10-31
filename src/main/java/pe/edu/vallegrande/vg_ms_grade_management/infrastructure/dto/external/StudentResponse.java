package pe.edu.vallegrande.vg_ms_grade_management.infrastructure.dto.external;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para respuesta del microservicio de estudiantes.
 * Contiene la información básica del estudiante.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentResponse {
    private String id;
    private String code;
    private String firstName;
    private String lastName;
    private String fullName;
    private String email;
    private String classroomId;
    private String status;
}
