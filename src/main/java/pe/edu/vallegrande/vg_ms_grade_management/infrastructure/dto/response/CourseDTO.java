package pe.edu.vallegrande.vg_ms_grade_management.infrastructure.dto.response;

import lombok.Data;

/**
 * DTO para mapear las respuestas del servicio externo de cursos
 */
@Data
public class CourseDTO {
    private String id;
    private String courseCode;
    private String name;
    private String description;
    private Integer credits;
    private String institutionId;
    private String level; // "PRIMARY", "SECONDARY", etc.
    private String status; // "A" = Activo, "I" = Inactivo
    private String teacherId;
    private String academicPeriod;
}