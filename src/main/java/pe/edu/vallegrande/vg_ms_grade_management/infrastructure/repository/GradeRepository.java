package pe.edu.vallegrande.vg_ms_grade_management.infrastructure.repository;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import pe.edu.vallegrande.vg_ms_grade_management.domain.enums.TypePeriod;
import pe.edu.vallegrande.vg_ms_grade_management.infrastructure.document.GradeDocument;
import reactor.core.publisher.Flux;

/**
 * Repositorio reactivo para Calificaciones según estándar MINEDU v8.0
 * Solo consultas a tabla propia del microservicio (sin JOINs cruzados)
 */
@Repository
public interface GradeRepository extends ReactiveMongoRepository<GradeDocument, String> {
    
    /**
     * Busca todas las calificaciones activas (status='A')
     * @return Flux de calificaciones activas
     */
    @Query("{ '$or': [ { 'status': 'A' }, { 'status': { '$exists': false } } ] }")
    Flux<GradeDocument> findByStatusActive();
    
    /**
     * Busca calificaciones por ID de teacher (solo activas)
     * @param teacherId ID del teacher
     * @return Flux de calificaciones
     */
    @Query("{ 'teacherId': ?0, '$or': [ { 'status': 'A' }, { 'status': { '$exists': false } } ] }")
    Flux<GradeDocument> findByTeacherIdAndStatusActive(String teacherId);
    
    /**
     * Busca calificaciones por ID de alumno y teacher (solo activas)
     * @param studentId ID del alumno
     * @param teacherId ID del teacher
     * @return Flux de calificaciones
     */
    @Query("{ 'studentId': ?0, 'teacherId': ?1, '$or': [ { 'status': 'A' }, { 'status': { '$exists': false } } ] }")
    Flux<GradeDocument> findByStudentIdAndTeacherIdAndStatusActive(String studentId, String teacherId);
    
    /**
     * Busca calificaciones por ID de aula, período y tipo de período (solo activas)
     * @param classroomId ID del aula
     * @param periodId ID del período
     * @param typePeriod Tipo de período
     * @return Flux de calificaciones
     */
    @Query("{ 'classroomId': ?0, 'periodId': ?1, 'typePeriod': ?2, '$or': [ { 'status': 'A' }, { 'status': { '$exists': false } } ] }")
    Flux<GradeDocument> findByClassroomIdAndPeriodIdAndTypePeriodAndStatusActive(
        String classroomId, String periodId, TypePeriod typePeriod);
    
    /**
     * Busca calificaciones por ID de aula, período, tipo de período y teacher (solo activas)
     * @param classroomId ID del aula
     * @param periodId ID del período
     * @param typePeriod Tipo de período
     * @param teacherId ID del teacher
     * @return Flux de calificaciones
     */
    @Query("{ 'classroomId': ?0, 'periodId': ?1, 'typePeriod': ?2, 'teacherId': ?3, '$or': [ { 'status': 'A' }, { 'status': { '$exists': false } } ] }")
    Flux<GradeDocument> findByClassroomIdAndPeriodIdAndTypePeriodAndTeacherIdAndStatusActive(
        String classroomId, String periodId, TypePeriod typePeriod, String teacherId);
    
    /**
     * Busca calificaciones por ID de curso y teacher (solo activas)
     * @param courseId ID del curso
     * @param teacherId ID del teacher
     * @return Flux de calificaciones
     */
    @Query("{ 'courseId': ?0, 'teacherId': ?1, '$or': [ { 'status': 'A' }, { 'status': { '$exists': false } } ] }")
    Flux<GradeDocument> findByCourseIdAndTeacherIdAndStatusActive(String courseId, String teacherId);
    
    /**
     * Busca calificaciones por ID de alumno (solo activas)
     * @param studentId ID del alumno
     * @return Flux de calificaciones
     */
    @Query("{ 'studentId': ?0, '$or': [ { 'status': 'A' }, { 'status': { '$exists': false } } ] }")
    Flux<GradeDocument> findByStudentIdAndStatusActive(String studentId);
    
    /**
     * Busca calificaciones por ID de período y tipo de período (solo activas)
     * @param periodId ID del período
     * @param typePeriod Tipo de período
     * @return Flux de calificaciones
     */
    @Query("{ 'periodId': ?0, 'typePeriod': ?1, '$or': [ { 'status': 'A' }, { 'status': { '$exists': false } } ] }")
    Flux<GradeDocument> findByPeriodIdAndTypePeriodAndStatusActive(String periodId, TypePeriod typePeriod);
    
    /**
     * Busca calificaciones por ID de aula y teacher (solo activas)
     * @param classroomId ID del aula
     * @param teacherId ID del teacher
     * @return Flux de calificaciones
     */
    @Query("{ 'classroomId': ?0, 'teacherId': ?1, '$or': [ { 'status': 'A' }, { 'status': { '$exists': false } } ] }")
    Flux<GradeDocument> findByClassroomIdAndTeacherIdAndStatusActive(String classroomId, String teacherId);
}