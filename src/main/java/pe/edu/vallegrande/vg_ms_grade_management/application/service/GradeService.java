package pe.edu.vallegrande.vg_ms_grade_management.application.service;

import pe.edu.vallegrande.vg_ms_grade_management.domain.model.Grade;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface GradeService {
    Flux<Grade> findAll();
    Flux<Grade> findAllInactive();
    Mono<Grade> findById(String id);
    Flux<Grade> findByStudentId(String studentId);
    Flux<Grade> findByCourseId(String courseId);
    Flux<Grade> findByStudentIdAndCourseId(String studentId, String courseId);
    Mono<Grade> save(Grade grade);
    Mono<Grade> update(String id, Grade grade);
    Mono<Grade> deleteById(String id);
    Mono<Grade> restoreById(String id);
}