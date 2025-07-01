package pe.edu.vallegrande.vg_ms_grade_management.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Modelo de dominio para Grade
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Grade {
    private String id;
    private String studentId;
    private String courseId;
    private String academicPeriod; // "Bimester", "Trimester", "Annual"
    private String evaluationType;
    private Double grade;
    private LocalDate evaluationDate;
    private String remarks;
    private Boolean deleted;
}