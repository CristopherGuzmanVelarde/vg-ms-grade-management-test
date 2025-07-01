package pe.edu.vallegrande.vg_ms_grade_management.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Modelo de dominio para Course
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    private String id;
    private String name;
    private String teacher;
    private Boolean deleted;
}