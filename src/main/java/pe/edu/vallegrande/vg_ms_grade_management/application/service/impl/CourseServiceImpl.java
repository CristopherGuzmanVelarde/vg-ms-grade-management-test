package pe.edu.vallegrande.vg_ms_grade_management.application.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pe.edu.vallegrande.vg_ms_grade_management.application.service.CourseService;
import pe.edu.vallegrande.vg_ms_grade_management.domain.model.Course;
import pe.edu.vallegrande.vg_ms_grade_management.infrastructure.mapper.CourseMapper;
import pe.edu.vallegrande.vg_ms_grade_management.infrastructure.repository.CourseRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Servicio para gestionar cursos
 */
@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private static final Logger logger = LoggerFactory.getLogger(CourseServiceImpl.class);
    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    /**
     * Obtiene todos los cursos
     * @return Flux de cursos
     */
    public Flux<Course> findAll() {
        return courseRepository.findAll()
                .map(courseMapper::toDomain)
                .filter(course -> course.getDeleted() == null || !course.getDeleted());
    }

    /**
     * Busca un curso por su ID
     * @param id ID del curso
     * @return Mono con el curso encontrado
     */
    public Mono<Course> findById(String id) {
        logger.info("Buscando curso con ID: {}", id);
        return courseRepository.findById(id)
                .doOnNext(doc -> logger.info("Curso encontrado en BD: {}", doc))
                .map(courseMapper::toDomain)
                .doOnNext(course -> logger.info("Curso mapeado: {}", course))
                .filter(course -> course.getDeleted() == null || !course.getDeleted())
                .doOnNext(course -> logger.info("Curso después del filtro: {}", course))
                .switchIfEmpty(Mono.fromRunnable(() -> logger.warn("No se encontró curso con ID: {}", id)));
    }

    /**
     * Guarda un nuevo curso
     * @param course Curso a guardar
     * @return Mono con el curso guardado
     */
    public Mono<Course> save(Course course) {
        course.setDeleted(false); // Establecer el campo deleted a false por defecto
        return courseRepository.save(courseMapper.toDocument(course))
                .map(courseMapper::toDomain);
    }

    /**
     * Actualiza un curso existente
     * @param id ID del curso a actualizar
     * @param course Datos actualizados del curso
     * @return Mono con el curso actualizado
     */
    public Mono<Course> update(String id, Course course) {
        return courseRepository.findById(id)
                .flatMap(existingCourse -> {
                    existingCourse.setName(course.getName());
                    existingCourse.setTeacher(course.getTeacher());
                    // No modificar el campo deleted aquí
                    return courseRepository.save(existingCourse);
                })
                .map(courseMapper::toDomain);
    }

    /**
     * Elimina un curso por su ID
     * @param id ID del curso a eliminar
     * @return Mono vacío
     */
    public Mono<Course> deleteById(String id) {
        return courseRepository.findById(id)
                .flatMap(courseDocument -> {
                    courseDocument.setDeleted(true);
                    return courseRepository.save(courseDocument);
                })
                .map(courseMapper::toDomain);
    }

    /**
     * Restaura un curso eliminado lógicamente
     * @param id ID del curso a restaurar
     * @return Mono con el curso restaurado
     */
    public Mono<Course> restoreById(String id) {
        return courseRepository.findById(id)
                .flatMap(courseDocument -> {
                    courseDocument.setDeleted(false);
                    return courseRepository.save(courseDocument);
                })
                .map(courseMapper::toDomain);
    }
}