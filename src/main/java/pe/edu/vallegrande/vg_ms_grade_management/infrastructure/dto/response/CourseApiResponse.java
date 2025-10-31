package pe.edu.vallegrande.vg_ms_grade_management.infrastructure.dto.response;

import lombok.Data;
import java.util.List;

/**
 * DTO para mapear las respuestas envueltas del servicio de cursos
 */
@Data
public class CourseApiResponse {
    private Boolean success;
    private String message;
    private CourseDTO data; // Para respuestas de un solo curso
    private List<CourseDTO> dataList; // Para respuestas de múltiples cursos
    private Integer total;
}