package pe.edu.vallegrande.vg_ms_grade_management.infrastructure.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pe.edu.vallegrande.vg_ms_grade_management.application.service.StudentService;
import pe.edu.vallegrande.vg_ms_grade_management.domain.model.Student;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Controlador REST para gestionar alumnos
 */
@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentRest {

    private final StudentService studentService;

    /**
     * Get all students
     * @return Flux of students
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<Student> getAllStudents() {
        return studentService.findAll();
    }

    /**
     * Get a student by ID
     * @param id ID of the student
     * @return Mono with the found student
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Student> getStudentById(@PathVariable Long id) {
        return studentService.findById(id);
    }

    /**
     * Create a new student
     * @param student Student to create
     * @return Mono with the created student
     */
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Student> createStudent(@RequestBody Student student) {
        return studentService.save(student);
    }

    /**
     * Update an existing student
     * @param id ID of the student to update
     * @param student Updated student data
     * @return Mono with the updated student
     */
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Student> updateStudent(@PathVariable Long id, @RequestBody Student student) {
        return studentService.update(id, student);
    }

    /**
     * Logically delete a student by ID
     * @param id ID of the student to delete
     * @return Mono with the logically deleted student
     */
    @DeleteMapping("/{id}")
    public Mono<Student> deleteStudent(@PathVariable Long id) {
        return studentService.deleteById(id);
    }

    /**
     * Restore a logically deleted student by ID
     * @param id ID of the student to restore
     * @return Mono with the restored student
     */
    @PutMapping("/{id}/restore")
    public Mono<Student> restoreStudent(@PathVariable Long id) {
        return studentService.restoreById(id);
    }
}