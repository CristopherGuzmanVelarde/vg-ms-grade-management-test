package pe.edu.vallegrande.vg_ms_grade_management.infrastructure.mapper;

import org.springframework.stereotype.Component;
import pe.edu.vallegrande.vg_ms_grade_management.domain.model.Course;
import pe.edu.vallegrande.vg_ms_grade_management.infrastructure.document.CourseDocument;

/**
 * Mapper para convertir entre Course (modelo de dominio) y CourseDocument (documento MongoDB)
 */
@Component
public class CourseMapper {

    /**
     * Convierte de CourseDocument a Course
     * @param document Documento de MongoDB
     * @return Modelo de dominio
     */
    public Course toDomain(CourseDocument document) {
        if (document == null) {
            return null;
        }
        
        return Course.builder()
                .id(document.getId())
                .name(document.getName())
                .teacher(document.getTeacher())
                .deleted(document.getDeleted())
                .build();
    }

    /**
     * Convierte de Course a CourseDocument
     * @param domain Modelo de dominio
     * @return Documento de MongoDB
     */
    public CourseDocument toDocument(Course domain) {
        if (domain == null) {
            return null;
        }
        
        return CourseDocument.builder()
                .id(domain.getId())
                .name(domain.getName())
                .teacher(domain.getTeacher())
                .deleted(domain.getDeleted())
                .build();
    }
}