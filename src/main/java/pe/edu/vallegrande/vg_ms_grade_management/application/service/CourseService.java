package pe.edu.vallegrande.vg_ms_grade_management.application.service;

import pe.edu.vallegrande.vg_ms_grade_management.domain.model.Course;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CourseService {
    Flux<Course> findAll();
    Mono<Course> findById(String id);
    Mono<Course> save(Course course);
    Mono<Course> update(String id, Course course);
    Mono<Course> deleteById(String id);
    Mono<Course> restoreById(String id);
}