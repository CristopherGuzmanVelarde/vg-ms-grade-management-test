package pe.edu.vallegrande.vg_ms_grade_management.infrastructure.dto.external;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para respuesta del microservicio de cursos.
 * Contiene la información del curso.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseResponse {
    private String id;
    private String code;
    private String name;
    private String description;
    private String educationLevel;
    private String status;
}
