package pe.edu.vallegrande.vg_ms_grade_management.infrastructure.dto.request;

import lombok.Data;

@Data
public class GradeRequest {
    private String studentId;
    private String courseId;
    private Double score;
}