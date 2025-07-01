package pe.edu.vallegrande.vg_ms_grade_management.application.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.vallegrande.vg_ms_grade_management.application.service.StudentService;
import pe.edu.vallegrande.vg_ms_grade_management.domain.model.Student;
import pe.edu.vallegrande.vg_ms_grade_management.infrastructure.mapper.StudentMapper;
import pe.edu.vallegrande.vg_ms_grade_management.infrastructure.repository.StudentRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import pe.edu.vallegrande.vg_ms_grade_management.application.service.SequenceGeneratorService;

/**
 * Servicio para gestionar students
 */
@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final SequenceGeneratorService sequenceGeneratorService;

    /**
     * Obtiene todos los students
     * @return Flux de students
     */
    public Flux<Student> findAll() {
        return studentRepository.findAll()
                .map(studentMapper::toDomain)
                .filter(student -> student.getDeleted() == null || !student.getDeleted());
    }

    /**
     * Busca un student por su ID
     * @param id ID del student
     * @return Mono con el student encontrado
     */
    public Mono<Student> findById(Long id) {
        return studentRepository.findById(id)
                .map(studentMapper::toDomain);
    }

    /**
     * Guarda un nuevo student
     * @param student Student a guardar
     * @return Mono con el student guardado
     */
    public Mono<Student> save(Student student) {
        return sequenceGeneratorService.generateSequence(Student.SEQUENCE_NAME)
                .flatMap(id -> {
                    student.setId(id);
                    student.setDeleted(false);
                    return studentRepository.save(studentMapper.toDocument(student))
                            .map(studentMapper::toDomain);
                });
    }

    /**
     * Actualiza un student existente
     * @param id ID del student a actualizar
     * @param student Datos actualizados del student
     * @return Mono con el student actualizado
     */
    public Mono<Student> update(Long id, Student student) {
        return studentRepository.findById(id)
                .flatMap(existingStudent -> {
                    existingStudent.setFirstName(student.getFirstName());
                    existingStudent.setLastName(student.getLastName());
                    existingStudent.setGradeSection(student.getGradeSection());
                    // No modificar el campo deleted aquí
                    return studentRepository.save(existingStudent);
                })
                .map(studentMapper::toDomain);
    }

    /**
     * Elimina un student por su ID
     * @param id ID del student a eliminar
     * @return Mono vacío
     */
    public Mono<Student> deleteById(Long id) {
        return studentRepository.findById(id)
                .flatMap(studentDocument -> {
                    studentDocument.setDeleted(true);
                    return studentRepository.save(studentDocument);
                })
                .map(studentMapper::toDomain);
    }

    /**
     * Restaura un student eliminado lógicamente
     * @param id ID del student a restaurar
     * @return Mono con el student restaurado
     */
    public Mono<Student> restoreById(Long id) {
        return studentRepository.findById(id)
                .flatMap(studentDocument -> {
                    studentDocument.setDeleted(false);
                    return studentRepository.save(studentDocument);
                })
                .map(studentMapper::toDomain);
    }
}