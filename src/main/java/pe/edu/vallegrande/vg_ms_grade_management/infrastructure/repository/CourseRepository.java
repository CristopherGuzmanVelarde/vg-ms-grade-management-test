package pe.edu.vallegrande.vg_ms_grade_management.infrastructure.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import pe.edu.vallegrande.vg_ms_grade_management.infrastructure.document.CourseDocument;

/**
 * Repositorio reactivo para Curso
 */
@Repository
public interface CourseRepository extends ReactiveMongoRepository<CourseDocument, String> {
}