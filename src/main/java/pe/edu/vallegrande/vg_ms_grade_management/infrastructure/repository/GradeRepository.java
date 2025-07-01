package pe.edu.vallegrande.vg_ms_grade_management.infrastructure.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import pe.edu.vallegrande.vg_ms_grade_management.infrastructure.document.GradeDocument;
import reactor.core.publisher.Flux;

/**
 * Repositorio reactivo para Calificación
 */
@Repository
public interface GradeRepository extends ReactiveMongoRepository<GradeDocument, String> {
    
    /**
     * Busca calificaciones por ID de alumno
     * @param alumnoId ID del alumno
     * @return Flux de calificaciones
     */
    Flux<GradeDocument> findByStudentId(String studentId);
    
    /**
     * Busca calificaciones por ID de curso
     * @param cursoId ID del curso
     * @return Flux de calificaciones
     */
    Flux<GradeDocument> findByCourseId(String courseId);
    
    /**
     * Busca calificaciones por ID de alumno y ID de curso
     * @param studentId ID del alumno
     * @param courseId ID del curso
     * @return Flux de calificaciones
     */
    Flux<GradeDocument> findByStudentIdAndCourseId(String studentId, String courseId);
}