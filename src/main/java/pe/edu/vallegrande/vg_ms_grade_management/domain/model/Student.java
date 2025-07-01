package pe.edu.vallegrande.vg_ms_grade_management.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Modelo de dominio para Student
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    public static final String SEQUENCE_NAME = "students_sequence";
    private Long id;
    private String firstName;
    private String lastName;
    private String gradeSection;
    private Boolean deleted;
}