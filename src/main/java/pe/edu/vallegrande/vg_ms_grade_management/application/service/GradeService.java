package pe.edu.vallegrande.vg_ms_grade_management.application.service;

import pe.edu.vallegrande.vg_ms_grade_management.domain.enums.TypePeriod;
import pe.edu.vallegrande.vg_ms_grade_management.domain.model.Grade;
import pe.edu.vallegrande.vg_ms_grade_management.infrastructure.dto.request.GradeRequest;
import pe.edu.vallegrande.vg_ms_grade_management.infrastructure.dto.request.GradeUpdateRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

/**
 * Servicio para gestión de calificaciones según estándar MINEDU v8.0
 * EXCLUSIVO para TEACHER
 */
public interface GradeService {
    
    // Métodos para TEACHER
    
    /**
     * Registrar calificación individual por competencia MINEDU
     * @param request Datos de la calificación
     * @param teacherId ID del teacher que registra
     * @return Mono con la calificación registrada
     */
    Mono<Grade> registerGrade(GradeRequest request, String teacherId);
    
    /**
     * Registrar calificaciones en lote
     * @param requests Lista de calificaciones
     * @param teacherId ID del teacher que registra
     * @return Flux de calificaciones registradas
     */
    Flux<Grade> registerBatchGrades(List<GradeRequest> requests, String teacherId);
    
    /**
     * Actualizar calificación existente
     * @param gradeId ID de la calificación
     * @param request Datos actualizados
     * @param teacherId ID del teacher que actualiza
     * @return Mono con la calificación actualizada
     */
    Mono<Grade> updateGrade(String gradeId, GradeUpdateRequest request, String teacherId);
    
    /**
     * Ver mis calificaciones por teacher
     * @param teacherId ID del teacher
     * @return Flux de calificaciones
     */
    Flux<Grade> findByTeacherId(String teacherId);
    
    /**
     * Reporte consolidado de aula por período
     * @param classroomId ID del aula
     * @param periodId ID del período
     * @param typePeriod Tipo de período
     * @param teacherId ID del teacher
     * @return Mono con el reporte
     */
    Mono<List<Map<String, Object>>> getClassroomPeriodReport(
        String classroomId, String periodId, TypePeriod typePeriod, String teacherId);
    
    /**
     * Historial de calificaciones de estudiante por teacher
     * @param studentId ID del estudiante
     * @param teacherId ID del teacher
     * @return Flux de calificaciones
     */
    Flux<Grade> findByStudentIdAndTeacherId(String studentId, String teacherId);
    
    /**
     * Eliminación lógica de una calificación (cambiar estado a I=Inactive)
     * @param gradeId ID de la calificación
     * @param teacherId ID del teacher que elimina
     * @return Mono con la calificación eliminada lógicamente
     */
    Mono<Grade> deleteGrade(String gradeId, String teacherId);
    
    /**
     * Restaurar una calificación eliminada (cambiar estado de I=Inactive a A=Active)
     * @param gradeId ID de la calificación
     * @param teacherId ID del teacher que restaura
     * @return Mono con la calificación restaurada
     */
    Mono<Grade> restoreGrade(String gradeId, String teacherId);
}