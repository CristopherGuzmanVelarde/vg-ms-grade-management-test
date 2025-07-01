package pe.edu.vallegrande.vg_ms_grade_management.infrastructure.mapper;

import org.springframework.stereotype.Component;
import pe.edu.vallegrande.vg_ms_grade_management.domain.model.Student;
import pe.edu.vallegrande.vg_ms_grade_management.infrastructure.document.StudentDocument;

/**
 * Mapper para convertir entre Student (modelo de dominio) y StudentDocument (documento MongoDB)
 */
@Component
public class StudentMapper {

    /**
     * Convierte de StudentDocument a Student
     * @param document Documento de MongoDB
     * @return Modelo de dominio
     */
    public Student toDomain(StudentDocument document) {
        if (document == null) {
            return null;
        }
        return Student.builder()
                .id(document.getId())
                .firstName(document.getFirstName())
                .lastName(document.getLastName())
                .gradeSection(document.getGradeSection())
                .deleted(document.getDeleted())
                .build();
    }

    /**
     * Convierte de Student a StudentDocument
     * @param domain Modelo de dominio
     * @return Documento de MongoDB
     */
    public StudentDocument toDocument(Student domain) {
        if (domain == null) {
            return null;
        }
        return StudentDocument.builder()
                .id(domain.getId())
                .firstName(domain.getFirstName())
                .lastName(domain.getLastName())
                .gradeSection(domain.getGradeSection())
                .deleted(domain.getDeleted())
                .build();
    }
}