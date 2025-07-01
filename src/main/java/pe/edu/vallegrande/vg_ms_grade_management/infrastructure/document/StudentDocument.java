package pe.edu.vallegrande.vg_ms_grade_management.infrastructure.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Documento MongoDB para Student
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "students")
public class StudentDocument {
    @Id
    private Long id;
    private String firstName;
    private String lastName;
    private String gradeSection;
    private Boolean deleted;
}