package pe.edu.vallegrande.vg_ms_grade_management.infrastructure.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

/**
 * Documento MongoDB para Grade
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "grades")
public class GradeDocument {
    @Id
    private String id;
    private String studentId;
    private String courseId;
    private String academicPeriod; // "Bimestre", "Trimestre", "Anual"
    private String evaluationType;
    private Double grade;
    private LocalDate evaluationDate;
    private String remarks;
    private Boolean deleted;
}