package pe.edu.vallegrande.vg_ms_grade_management.application.service;

import pe.edu.vallegrande.vg_ms_grade_management.domain.model.Student;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface StudentService {
    Flux<Student> findAll();
    Mono<Student> findById(Long id);
    Mono<Student> save(Student student);
    Mono<Student> update(Long id, Student student);
    Mono<Student> deleteById(Long id);
    Mono<Student> restoreById(Long id);
}