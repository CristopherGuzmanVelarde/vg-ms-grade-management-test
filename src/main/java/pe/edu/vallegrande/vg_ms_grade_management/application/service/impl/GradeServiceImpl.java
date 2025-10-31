package pe.edu.vallegrande.vg_ms_grade_management.application.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pe.edu.vallegrande.vg_ms_grade_management.application.service.GradeService;
import pe.edu.vallegrande.vg_ms_grade_management.domain.enums.EvaluationType;
import pe.edu.vallegrande.vg_ms_grade_management.domain.enums.GradeScale;
import pe.edu.vallegrande.vg_ms_grade_management.domain.enums.TypePeriod;
import pe.edu.vallegrande.vg_ms_grade_management.domain.model.Grade;
import pe.edu.vallegrande.vg_ms_grade_management.infrastructure.document.GradeDocument;
import pe.edu.vallegrande.vg_ms_grade_management.infrastructure.dto.request.GradeRequest;
import pe.edu.vallegrande.vg_ms_grade_management.infrastructure.dto.request.GradeUpdateRequest;
import pe.edu.vallegrande.vg_ms_grade_management.infrastructure.exception.ResourceNotFoundException;
import pe.edu.vallegrande.vg_ms_grade_management.infrastructure.mapper.GradeMapper;
import pe.edu.vallegrande.vg_ms_grade_management.infrastructure.repository.GradeRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementación del servicio de gestión de calificaciones según estándar MINEDU v8.0
 * EXCLUSIVO para TEACHER
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class GradeServiceImpl implements GradeService {

    private final GradeRepository gradeRepository;
    private final GradeMapper gradeMapper;

    @Override
    public Mono<Grade> registerGrade(GradeRequest request, String teacherId) {
        log.info("Registering grade for student {} by teacher {}", request.getStudentId(), teacherId);
        
        Grade grade = gradeMapper.fromRequest(request, teacherId);
        grade.setCreatedAt(LocalDateTime.now());
        grade.setUpdatedAt(LocalDateTime.now());
        
        return gradeRepository.save(gradeMapper.toDocument(grade))
                .map(gradeMapper::toDomain)
                .doOnSuccess(savedGrade -> 
                    log.info("Grade registered successfully with ID: {}", savedGrade.getId()));
    }

    @Override
    public Flux<Grade> registerBatchGrades(List<GradeRequest> requests, String teacherId) {
        log.info("Registering batch grades, total: {}", requests.size());
        
        return Flux.fromIterable(requests)
                .flatMap(request -> registerGrade(request, teacherId))
                .doOnComplete(() -> log.info("Batch registration completed"));
    }

    @Override
    public Mono<Grade> updateGrade(String gradeId, GradeUpdateRequest request, String teacherId) {
        log.info("Updating grade {} by teacher {}", gradeId, teacherId);
        
        return gradeRepository.findById(gradeId)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Grade", gradeId)))
                .flatMap(existingGrade -> {
                    // Verificar que el teacher sea el dueño de la calificación
                    if (!existingGrade.getTeacherId().equals(teacherId)) {
                        return Mono.error(new IllegalArgumentException(
                            "No tiene permiso para actualizar esta calificación"));
                    }
                    
                    // Actualizar solo los campos editables (NO los IDs de referencia)
                    existingGrade.setCompetenceName(request.getCompetenceName());
                    existingGrade.setCapacityEvaluated(request.getCapacityEvaluated());
                    existingGrade.setGradeScale(request.getGradeScale());
                    
                    // Convertir numericGrade de Double a BigDecimal
                    if (request.getNumericGrade() != null) {
                        existingGrade.setNumericGrade(BigDecimal.valueOf(request.getNumericGrade()));
                    } else {
                        existingGrade.setNumericGrade(null);
                    }
                    
                    // Convertir evaluationType de String a Enum
                    try {
                        existingGrade.setEvaluationType(
                            EvaluationType.valueOf(request.getEvaluationType().toUpperCase())
                        );
                    } catch (IllegalArgumentException e) {
                        return Mono.error(new IllegalArgumentException(
                            "Tipo de evaluación inválido: " + request.getEvaluationType()));
                    }
                    
                    // Convertir evaluationDate de String a LocalDate
                    try {
                        existingGrade.setEvaluationDate(LocalDate.parse(request.getEvaluationDate()));
                    } catch (Exception e) {
                        return Mono.error(new IllegalArgumentException(
                            "Formato de fecha inválido. Use YYYY-MM-DD"));
                    }
                    
                    existingGrade.setObservations(request.getObservations());
                    // Mantener el estado en "A" (Active) sin cambios
                    existingGrade.setUpdatedAt(LocalDateTime.now());
                    
                    return gradeRepository.save(existingGrade);
                })
                .map(gradeMapper::toDomain)
                .doOnSuccess(grade -> log.info("Grade updated successfully: {}", gradeId));
    }

    @Override
    public Flux<Grade> findByTeacherId(String teacherId) {
        log.info("Finding grades for teacher: {}", teacherId);
        return gradeRepository.findByTeacherIdAndStatusActive(teacherId)
                .map(gradeMapper::toDomain);
    }

    @Override
    public Mono<List<Map<String, Object>>> getClassroomPeriodReport(
            String classroomId, String periodId, TypePeriod typePeriod, String teacherId) {
        
        log.info("Generating report for classroom {} period {} type {} by teacher {}", 
                classroomId, periodId, typePeriod, teacherId);
        
        return gradeRepository.findByClassroomIdAndPeriodIdAndTypePeriodAndTeacherIdAndStatusActive(
                    classroomId, periodId, typePeriod, teacherId)
                .collectList()
                .map(grades -> {
                    // Agrupar por curso y calcular distribución de calificaciones
                    Map<String, Map<String, Object>> coursesSummary = new HashMap<>();
                    
                    for (GradeDocument grade : grades) {
                        String courseId = grade.getCourseId();
                        coursesSummary.putIfAbsent(courseId, new HashMap<>());
                        Map<String, Object> courseData = coursesSummary.get(courseId);
                        
                        // Inicializar si no existe
                        if (!courseData.containsKey("course_id")) {
                            courseData.put("course_id", courseId);
                            courseData.put("total_grades", 0);
                            courseData.put("grade_distribution", new HashMap<String, Integer>());
                        }
                        
                        // Incrementar contador total
                        int total = (int) courseData.get("total_grades");
                        courseData.put("total_grades", total + 1);
                        
                        // Actualizar distribución
                        @SuppressWarnings("unchecked")
                        Map<String, Integer> distribution = (Map<String, Integer>) courseData.get("grade_distribution");
                        GradeScale scale = grade.getGradeScale();
                        if (scale != null) {
                            distribution.put(scale.name(), distribution.getOrDefault(scale.name(), 0) + 1);
                        }
                    }
                    
                    return new ArrayList<>(coursesSummary.values());
                });
    }

    @Override
    public Flux<Grade> findByStudentIdAndTeacherId(String studentId, String teacherId) {
        log.info("Finding grades for student {} by teacher {}", studentId, teacherId);
        return gradeRepository.findByStudentIdAndTeacherIdAndStatusActive(studentId, teacherId)
                .map(gradeMapper::toDomain);
    }

    @Override
    public Mono<Grade> deleteGrade(String gradeId, String teacherId) {
        log.info("Deleting grade {} by teacher {}", gradeId, teacherId);
        
        return gradeRepository.findById(gradeId)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Grade", gradeId)))
                .flatMap(existingGrade -> {
                    // Verificar que el teacher sea el dueño de la calificación
                    if (!existingGrade.getTeacherId().equals(teacherId)) {
                        return Mono.error(new IllegalArgumentException(
                            "No tiene permiso para eliminar esta calificación"));
                    }
                    
                    // Verificar que la calificación no esté ya inactiva
                    if ("I".equals(existingGrade.getStatus())) {
                        return Mono.error(new IllegalArgumentException(
                            "La calificación ya está inactiva"));
                    }
                    
                    // Cambiar estado a I (Inactive) - Eliminación lógica
                    existingGrade.setStatus("I");
                    existingGrade.setUpdatedAt(LocalDateTime.now());
                    
                    return gradeRepository.save(existingGrade);
                })
                .map(gradeMapper::toDomain)
                .doOnSuccess(grade -> log.info("Grade deleted successfully: {}", gradeId));
    }

    @Override
    public Mono<Grade> restoreGrade(String gradeId, String teacherId) {
        log.info("Restoring grade {} by teacher {}", gradeId, teacherId);
        
        return gradeRepository.findById(gradeId)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Grade", gradeId)))
                .flatMap(existingGrade -> {
                    // Verificar que el teacher sea el dueño de la calificación
                    if (!existingGrade.getTeacherId().equals(teacherId)) {
                        return Mono.error(new IllegalArgumentException(
                            "No tiene permiso para restaurar esta calificación"));
                    }
                    
                    // Verificar que la calificación esté inactiva
                    if (!"I".equals(existingGrade.getStatus())) {
                        return Mono.error(new IllegalArgumentException(
                            "La calificación no está inactiva, no se puede restaurar"));
                    }
                    
                    // Cambiar estado a A (Active) - Restaurar
                    existingGrade.setStatus("A");
                    existingGrade.setUpdatedAt(LocalDateTime.now());
                    
                    return gradeRepository.save(existingGrade);
                })
                .map(gradeMapper::toDomain)
                .doOnSuccess(grade -> log.info("Grade restored successfully: {}", gradeId));
    }
}