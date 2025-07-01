package pe.edu.vallegrande.vg_ms_grade_management.infrastructure.mapper;

import org.springframework.stereotype.Component;
import pe.edu.vallegrande.vg_ms_grade_management.domain.model.Grade;
import pe.edu.vallegrande.vg_ms_grade_management.infrastructure.document.GradeDocument;

/**
 * Mapper para convertir entre Grade (modelo de dominio) y GradeDocument (documento MongoDB)
 */
@Component
public class GradeMapper {

    /**
     * Convierte de GradeDocument a Grade
     * @param document Documento de MongoDB
     * @return Modelo de dominio
     */
    public Grade toDomain(GradeDocument document) {
        if (document == null) {
            return null;
        }
        return Grade.builder()
                .id(document.getId())
                .studentId(document.getStudentId())
                .courseId(document.getCourseId())
                .academicPeriod(document.getAcademicPeriod())
                .evaluationType(document.getEvaluationType())
                .grade(document.getGrade())
                .evaluationDate(document.getEvaluationDate())
                .remarks(document.getRemarks())
                .deleted(document.getDeleted())
                .build();
    }

    /**
     * Convierte de Grade a GradeDocument
     * @param domain Modelo de dominio
     * @return Documento de MongoDB
     */
    public GradeDocument toDocument(Grade domain) {
        if (domain == null) {
            return null;
        }
        return GradeDocument.builder()
                .id(domain.getId())
                .studentId(domain.getStudentId())
                .courseId(domain.getCourseId())
                .academicPeriod(domain.getAcademicPeriod())
                .evaluationType(domain.getEvaluationType())
                .grade(domain.getGrade())
                .evaluationDate(domain.getEvaluationDate())
                .remarks(domain.getRemarks())
                .deleted(domain.getDeleted())
                .build();
    }
}