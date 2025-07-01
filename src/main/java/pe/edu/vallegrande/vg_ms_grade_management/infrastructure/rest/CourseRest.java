package pe.edu.vallegrande.vg_ms_grade_management.infrastructure.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pe.edu.vallegrande.vg_ms_grade_management.application.service.CourseService;
import pe.edu.vallegrande.vg_ms_grade_management.domain.model.Course;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Controlador REST para gestionar cursos
 */
@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseRest {

    private final CourseService courseService;

    /**
     * Get all courses
     * @return Flux of courses
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<Course> getAllCourses() {
        return courseService.findAll();
    }

    /**
     * Get a course by ID
     * @param id ID of the course
     * @return Mono with the found course
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Course> getCourseById(@PathVariable String id) {
        return courseService.findById(id);
    }

    /**
     * Create a new course
     * @param course Course to create
     * @return Mono with the created course
     */
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Course> createCourse(@RequestBody Course course) {
        return courseService.save(course);
    }

    /**
     * Update an existing course
     * @param id ID of the course to update
     * @param course Updated course data
     * @return Mono with the updated course
     */
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Course> updateCourse(@PathVariable String id, @RequestBody Course course) {
        return courseService.update(id, course);
    }

    /**
     * Logically delete a course by ID
     * @param id ID of the course to delete
     * @return Mono with the logically deleted course
     */
    @DeleteMapping("/{id}")
    public Mono<Course> deleteCourse(@PathVariable String id) {
        return courseService.deleteById(id);
    }

    /**
     * Restore a logically deleted course by ID
     * @param id ID of the course to restore
     * @return Mono with the restored course
     */
    @PutMapping("/{id}/restore")
    public Mono<Course> restoreCourse(@PathVariable String id) {
        return courseService.restoreById(id);
    }
}