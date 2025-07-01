package pe.edu.vallegrande.vg_ms_grade_management.infrastructure.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Documento MongoDB para Course
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "courses")
public class CourseDocument {
    @Id
    private String id;
    private String name;
    private String teacher;
    private Boolean deleted;
}