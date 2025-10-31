package pe.edu.vallegrande.vg_ms_grade_management.infrastructure.mapper;

import org.springframework.stereotype.Component;
import pe.edu.vallegrande.vg_ms_grade_management.domain.model.Grade;
import pe.edu.vallegrande.vg_ms_grade_management.infrastructure.document.GradeDocument;
import pe.edu.vallegrande.vg_ms_grade_management.infrastructure.dto.request.GradeRequest;
import pe.edu.vallegrande.vg_ms_grade_management.infrastructure.dto.response.GradeResponse;

/**
 * Mapper para convertir entre Grade (modelo de dominio) y GradeDocument (documento MongoDB)
 * Actualizado para estándar MINEDU v8.0
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
                .classroomId(document.getClassroomId())
                .periodId(document.getPeriodId())
                .typePeriod(document.getTypePeriod())
                .teacherId(document.getTeacherId())
                .competenceName(document.getCompetenceName())
                .capacityEvaluated(document.getCapacityEvaluated())
                .gradeScale(document.getGradeScale())
                .numericGrade(document.getNumericGrade())
                .evaluationType(document.getEvaluationType())
                .evaluationDate(document.getEvaluationDate())
                .observations(document.getObservations())
                .status(document.getStatus())
                .createdAt(document.getCreatedAt())
                .updatedAt(document.getUpdatedAt())
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
                .classroomId(domain.getClassroomId())
                .periodId(domain.getPeriodId())
                .typePeriod(domain.getTypePeriod())
                .teacherId(domain.getTeacherId())
                .competenceName(domain.getCompetenceName())
                .capacityEvaluated(domain.getCapacityEvaluated())
                .gradeScale(domain.getGradeScale())
                .numericGrade(domain.getNumericGrade())
                .evaluationType(domain.getEvaluationType())
                .evaluationDate(domain.getEvaluationDate())
                .observations(domain.getObservations())
                .status(domain.getStatus())
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .build();
    }

    /**
     * Convierte de GradeRequest a Grade
     * @param request DTO de request
     * @param teacherId ID del teacher que registra la calificación
     * @return Modelo de dominio
     */
    public Grade fromRequest(GradeRequest request, String teacherId) {
        if (request == null) {
            return null;
        }
        return Grade.builder()
                .studentId(request.getStudentId())
                .courseId(request.getCourseId())
                .classroomId(request.getClassroomId())
                .periodId(request.getPeriodId())
                .typePeriod(request.getTypePeriod())
                .teacherId(teacherId)
                .competenceName(request.getCompetenceName())
                .capacityEvaluated(request.getCapacityEvaluated())
                .gradeScale(request.getGradeScale())
                .numericGrade(request.getNumericGrade())
                .evaluationType(request.getEvaluationType())
                .evaluationDate(request.getEvaluationDate())
                .observations(request.getObservations())
                .status("A") // Por defecto activo
                .build();
    }

    /**
     * Convierte de Grade a GradeResponse
     * @param domain Modelo de dominio
     * @return DTO de response
     */
    public GradeResponse toResponse(Grade domain) {
        if (domain == null) {
            return null;
        }
        GradeResponse response = new GradeResponse();
        response.setId(domain.getId());
        response.setStudentId(domain.getStudentId());
        response.setCourseId(domain.getCourseId());
        response.setClassroomId(domain.getClassroomId());
        response.setPeriodId(domain.getPeriodId());
        response.setTypePeriod(domain.getTypePeriod());
        response.setTeacherId(domain.getTeacherId());
        response.setCompetenceName(domain.getCompetenceName());
        response.setCapacityEvaluated(domain.getCapacityEvaluated());
        response.setGradeScale(domain.getGradeScale());
        response.setNumericGrade(domain.getNumericGrade());
        response.setEvaluationType(domain.getEvaluationType());
        response.setEvaluationDate(domain.getEvaluationDate());
        response.setObservations(domain.getObservations());
        response.setStatus(domain.getStatus());
        response.setCreatedAt(domain.getCreatedAt());
        response.setUpdatedAt(domain.getUpdatedAt());
        return response;
    }
}