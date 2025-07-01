package pe.edu.vallegrande.vg_ms_grade_management.infrastructure.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import pe.edu.vallegrande.vg_ms_grade_management.infrastructure.document.StudentDocument;

/**
 * Repositorio reactivo para Alumno
 */
@Repository
public interface StudentRepository extends ReactiveMongoRepository<StudentDocument, Long> {
}