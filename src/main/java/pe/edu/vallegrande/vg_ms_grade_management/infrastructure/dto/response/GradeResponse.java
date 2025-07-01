package pe.edu.vallegrande.vg_ms_grade_management.infrastructure.dto.response;

import lombok.Data;

@Data
public class GradeResponse {
    private String id;
    private String studentId;
    private String courseId;
    private Double score;
    private Boolean deleted;
}