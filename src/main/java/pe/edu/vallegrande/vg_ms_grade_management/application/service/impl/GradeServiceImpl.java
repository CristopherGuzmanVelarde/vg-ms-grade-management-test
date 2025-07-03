package pe.edu.vallegrande.vg_ms_grade_management.application.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pe.edu.vallegrande.vg_ms_grade_management.application.service.GradeService;
import pe.edu.vallegrande.vg_ms_grade_management.application.service.GradeNotificationService;
import pe.edu.vallegrande.vg_ms_grade_management.domain.model.Grade;
import pe.edu.vallegrande.vg_ms_grade_management.infrastructure.mapper.GradeMapper;
import pe.edu.vallegrande.vg_ms_grade_management.infrastructure.repository.GradeRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Servicio para gestionar calificaciones
 */
@Service
@RequiredArgsConstructor
public class GradeServiceImpl implements GradeService {

    private final GradeRepository gradeRepository;
    private final GradeMapper gradeMapper;
    private final GradeNotificationService gradeNotificationService;
    private static final Logger logger = LoggerFactory.getLogger(GradeServiceImpl.class);

    /**
     * Obtiene todas las calificaciones no eliminadas
     * @return Flux de calificaciones
     */
    public Flux<Grade> findAll() {
        return gradeRepository.findAll()
                .filter(gradeDocument -> !Boolean.TRUE.equals(gradeDocument.getDeleted()))
                .map(gradeMapper::toDomain);
    }

    /**
     * Obtiene todas las calificaciones inactivas (eliminadas lógicamente)
     * @return Flux de calificaciones inactivas
     */
    public Flux<Grade> findAllInactive() {
        return gradeRepository.findAll()
                .filter(gradeDocument -> Boolean.TRUE.equals(gradeDocument.getDeleted()))
                .map(gradeMapper::toDomain);
    }

    /**
     * Busca una calificación por su ID, incluyendo las eliminadas lógicamente
     * @param id ID de la calificación
     * @return Mono con la calificación encontrada
     */
    public Mono<Grade> findById(String id) {
        return gradeRepository.findById(id)
                .filter(gradeDocument -> !Boolean.TRUE.equals(gradeDocument.getDeleted()))
                .map(gradeMapper::toDomain);
    }

    /**
     * Busca calificaciones por ID de alumno
     * @param studentId ID del alumno
     * @return Flux de calificaciones
     */
    public Flux<Grade> findByStudentId(String studentId) {
        return gradeRepository.findByStudentId(studentId)
                .map(gradeMapper::toDomain);
    }

    /**
     * Busca calificaciones por ID de curso
     * @param courseId ID del curso
     * @return Flux de calificaciones
     */
    public Flux<Grade> findByCourseId(String courseId) {
        return gradeRepository.findByCourseId(courseId)
                .map(gradeMapper::toDomain);
    }

    /**
     * Busca calificaciones por ID de alumno y ID de curso
     * @param studentId ID del alumno
     * @param courseId ID del curso
     * @return Flux de calificaciones
     */
    public Flux<Grade> findByStudentIdAndCourseId(String studentId, String courseId) {
        return gradeRepository.findByStudentIdAndCourseId(studentId, courseId)
                .map(gradeMapper::toDomain);
    }

    /**
     * Guarda una nueva calificación y genera notificaciones automáticas
     * @param grade Calificación a guardar
     * @return Mono con la calificación guardada
     */
    public Mono<Grade> save(Grade grade) {
        grade.setDeleted(false);
        logger.info("Guardando calificación: {}", grade);
        return gradeRepository.save(gradeMapper.toDocument(grade))
                .map(gradeMapper::toDomain)
                .doOnNext(savedGrade -> logger.info("Calificación guardada exitosamente: {}", savedGrade))
                .flatMap(savedGrade -> {
                    logger.info("Iniciando proceso de notificaciones para calificación: {}", savedGrade.getId());
                    return gradeNotificationService.processGradeNotifications(savedGrade, true)
                        .doOnSuccess(result -> logger.info("Notificaciones procesadas exitosamente para calificación: {}", result.getId()))
                        .onErrorResume(e -> {
                            logger.error("Error al generar la notificación para calificación {}: ", savedGrade.getId(), e);
                            return Mono.just(savedGrade);
                        });
                });
    }

    /**
     * Actualiza una calificación existente y genera notificaciones automáticas
     * @param id ID de la calificación a actualizar
     * @param grade Datos actualizados de la calificación
     * @return Mono con la calificación actualizada
     */
    public Mono<Grade> update(String id, Grade grade) {
        return gradeRepository.findById(id)
                .flatMap(existingGrade -> {
                    existingGrade.setStudentId(grade.getStudentId());
                    existingGrade.setCourseId(grade.getCourseId());
                    existingGrade.setGrade(grade.getGrade());
                    existingGrade.setAcademicPeriod(grade.getAcademicPeriod());
                    existingGrade.setEvaluationType(grade.getEvaluationType());
                    existingGrade.setEvaluationDate(grade.getEvaluationDate());
                    existingGrade.setRemarks(grade.getRemarks());
                    // No modificar el campo deleted aquí
                    return gradeRepository.save(existingGrade);
                })
                .map(gradeMapper::toDomain)
                .flatMap(updatedGrade -> gradeNotificationService.processGradeNotifications(updatedGrade, false));
    }

    /**
     * Elimina lógicamente una calificación por su ID
     * @param id ID de la calificación a eliminar
     * @return Mono con la calificación eliminada
     */
    public Mono<Grade> deleteById(String id) {
        return gradeRepository.findById(id)
                .flatMap(gradeDocument -> {
                    gradeDocument.setDeleted(true);
                    return gradeRepository.save(gradeDocument);
                })
                .map(gradeMapper::toDomain);
    }

    /**
     * Restaura una calificación eliminada lógicamente por su ID
     * @param id ID de la calificación a restaurar
     * @return Mono con la calificación restaurada
     */
    public Mono<Grade> restoreById(String id) {
        return gradeRepository.findById(id)
                .flatMap(gradeDocument -> {
                    gradeDocument.setDeleted(false);
                    return gradeRepository.save(gradeDocument);
                })
                .map(gradeMapper::toDomain);
    }
}