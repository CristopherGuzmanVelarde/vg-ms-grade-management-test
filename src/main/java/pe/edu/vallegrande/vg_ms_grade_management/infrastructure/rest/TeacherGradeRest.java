package pe.edu.vallegrande.vg_ms_grade_management.infrastructure.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import pe.edu.vallegrande.vg_ms_grade_management.application.service.GradeService;
import pe.edu.vallegrande.vg_ms_grade_management.domain.enums.TypePeriod;
import pe.edu.vallegrande.vg_ms_grade_management.infrastructure.dto.request.GradeRequest;
import pe.edu.vallegrande.vg_ms_grade_management.infrastructure.dto.request.GradeUpdateRequest;
import pe.edu.vallegrande.vg_ms_grade_management.infrastructure.mapper.GradeMapper;
import pe.edu.vallegrande.vg_ms_grade_management.infrastructure.validation.HeaderValidator;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * REST Controller para gestión de calificaciones - EXCLUSIVO TEACHER
 * Sistema de registro de calificaciones basado en el estándar MINEDU v8.0
 * 
 * Headers Obligatorios:
 * - X-User-Id: ID del teacher
 * - X-User-Roles: Debe contener "teacher"
 * - X-Institution-Id: ID de la institución
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/grades/teacher")
@RequiredArgsConstructor
public class TeacherGradeRest {


    private final GradeService gradeService;
    private final GradeMapper gradeMapper;

    /**
     * POST /api/v1/grades/teacher/register-grade
     * Registrar calificación individual por competencia MINEDU
     */
    @PostMapping(value = "/register-grade", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Map<String, Object>>> registerGrade(
            @Valid @RequestBody GradeRequest request,
            ServerWebExchange exchange) {
        
        return HeaderValidator.validateTeacherHeaders(exchange.getRequest())
                .flatMap(headers -> {
                    String teacherId = headers.getUserId().toString();
                    log.info("Teacher {} registering grade for student {}", teacherId, request.getStudentId());
                    
                    return gradeService.registerGrade(request, teacherId)
                            .map(grade -> {
                                Map<String, Object> response = new HashMap<>();
                                response.put("message", "Calificación registrada exitosamente");
                                response.put("grade", gradeMapper.toResponse(grade));
                                return ResponseEntity.status(HttpStatus.CREATED).body(response);
                            });
                });
    }

    /**
     * POST /api/v1/grades/teacher/register-batch-grades
     * Registrar calificaciones en lote por aula y período
     */
    @PostMapping(value = "/register-batch-grades", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Map<String, Object>>> registerBatchGrades(
            @RequestBody Map<String, Object> payload,
            ServerWebExchange exchange) {
        
        return HeaderValidator.validateTeacherHeaders(exchange.getRequest())
                .flatMap(headers -> {
                    String teacherId = headers.getUserId().toString();
                    @SuppressWarnings("unchecked")
                    List<GradeRequest> grades = (List<GradeRequest>) payload.get("grades");
                    
                    log.info("Teacher {} registering batch grades, total: {}", teacherId, grades.size());
                    
                    return gradeService.registerBatchGrades(grades, teacherId)
                            .collectList()
                            .map(savedGrades -> {
                                Map<String, Object> response = new HashMap<>();
                                response.put("message", "Calificaciones en lote registradas exitosamente");
                                response.put("total_processed", grades.size());
                                response.put("total_created", savedGrades.size());
                                return ResponseEntity.ok(response);
                            });
                });
    }

    /**
     * PUT /api/v1/grades/teacher/update-grade/{grade_id}
     * Actualizar calificación existente
     */
    @PutMapping(value = "/update-grade/{gradeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Map<String, Object>>> updateGrade(
            @PathVariable String gradeId,
            @Valid @RequestBody GradeUpdateRequest request,
            ServerWebExchange exchange) {
        
        return HeaderValidator.validateTeacherHeaders(exchange.getRequest())
                .flatMap(headers -> {
                    String teacherId = headers.getUserId().toString();
                    log.info("Teacher {} updating grade {}", teacherId, gradeId);
                    
                    return gradeService.updateGrade(gradeId, request, teacherId)
                            .map(grade -> {
                                Map<String, Object> response = new HashMap<>();
                                response.put("message", "Calificación actualizada exitosamente");
                                response.put("grade", gradeMapper.toResponse(grade));
                                return ResponseEntity.ok(response);
                            });
                });
    }

    /**
     * GET /api/v1/grades/teacher/my-grades
     * Ver mis calificaciones por período y aula
     */
    @GetMapping(value = "/my-grades", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Map<String, Object>>> getMyGrades(ServerWebExchange exchange) {
        
        return HeaderValidator.validateTeacherHeaders(exchange.getRequest())
                .flatMap(headers -> {
                    String teacherId = headers.getUserId().toString();
                    log.info("Teacher {} retrieving all their grades", teacherId);
                    
                    return gradeService.findByTeacherId(teacherId)
                            .map(gradeMapper::toResponse)
                            .collectList()
                            .map(grades -> {
                                Map<String, Object> response = new HashMap<>();
                                response.put("message", "Mis calificaciones obtenidas exitosamente");
                                response.put("total_grades", grades.size());
                                response.put("grades", grades);
                                return ResponseEntity.ok(response);
                            });
                });
    }

    /**
     * GET /api/v1/grades/teacher/classroom/{classroom_id}/period/{period_id}/type/{type_period}/report
     * Reporte consolidado de mi aula por período evaluativo específico
     */
    @GetMapping(value = "/classroom/{classroomId}/period/{periodId}/type/{typePeriod}/report", 
                produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Map<String, Object>>> getClassroomPeriodReport(
            @PathVariable String classroomId,
            @PathVariable String periodId,
            @PathVariable TypePeriod typePeriod,
            ServerWebExchange exchange) {
        
        return HeaderValidator.validateTeacherHeaders(exchange.getRequest())
                .flatMap(headers -> {
                    String teacherId = headers.getUserId().toString();
                    log.info("Teacher {} generating report for classroom {} period {} type {}", 
                            teacherId, classroomId, periodId, typePeriod);
                    
                    return gradeService.getClassroomPeriodReport(classroomId, periodId, typePeriod, teacherId)
                            .map(report -> {
                                Map<String, Object> response = new HashMap<>();
                                response.put("message", "Reporte de aula obtenido exitosamente");
                                response.put("classroom_id", classroomId);
                                response.put("period_id", periodId);
                                response.put("type_period", typePeriod);
                                response.put("courses_summary", report);
                                return ResponseEntity.ok(response);
                            });
                });
    }

    /**
     * GET /api/v1/grades/teacher/student/{student_id}/grades
     * Historial de calificaciones de estudiante en mis aulas
     */
    @GetMapping(value = "/student/{studentId}/grades", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Map<String, Object>>> getStudentGrades(
            @PathVariable String studentId,
            ServerWebExchange exchange) {
        
        return HeaderValidator.validateTeacherHeaders(exchange.getRequest())
                .flatMap(headers -> {
                    String teacherId = headers.getUserId().toString();
                    log.info("Teacher {} retrieving grades for student {}", teacherId, studentId);
                    
                    return gradeService.findByStudentIdAndTeacherId(studentId, teacherId)
                            .map(gradeMapper::toResponse)
                            .collectList()
                            .map(grades -> {
                                Map<String, Object> response = new HashMap<>();
                                response.put("message", "Historial de estudiante obtenido exitosamente");
                                response.put("student_id", studentId);
                                response.put("total_grades", grades.size());
                                response.put("grades", grades);
                                return ResponseEntity.ok(response);
                            });
                });
    }

    /**
     * DELETE /api/v1/grades/teacher/delete-grade/{grade_id}
     * Eliminación lógica de una calificación (cambia estado a I=Inactive)
     */
    @DeleteMapping(value = "/delete-grade/{gradeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Map<String, Object>>> deleteGrade(
            @PathVariable String gradeId,
            ServerWebExchange exchange) {
        
        return HeaderValidator.validateTeacherHeaders(exchange.getRequest())
                .flatMap(headers -> {
                    String teacherId = headers.getUserId().toString();
                    log.info("Teacher {} deleting grade {}", teacherId, gradeId);
                    
                    return gradeService.deleteGrade(gradeId, teacherId)
                            .map(grade -> {
                                Map<String, Object> response = new HashMap<>();
                                response.put("message", "Calificación eliminada lógicamente exitosamente");
                                response.put("grade", gradeMapper.toResponse(grade));
                                return ResponseEntity.ok(response);
                            });
                });
    }

    /**
     * PATCH /api/v1/grades/teacher/restore-grade/{grade_id}
     * Restaurar una calificación eliminada (cambia estado de I=Inactive a A=Active)
     */
    @PatchMapping(value = "/restore-grade/{gradeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Map<String, Object>>> restoreGrade(
            @PathVariable String gradeId,
            ServerWebExchange exchange) {
        
        return HeaderValidator.validateTeacherHeaders(exchange.getRequest())
                .flatMap(headers -> {
                    String teacherId = headers.getUserId().toString();
                    log.info("Teacher {} restoring grade {}", teacherId, gradeId);
                    
                    return gradeService.restoreGrade(gradeId, teacherId)
                            .map(grade -> {
                                Map<String, Object> response = new HashMap<>();
                                response.put("message", "Calificación restaurada exitosamente");
                                response.put("grade", gradeMapper.toResponse(grade));
                                return ResponseEntity.ok(response);
                            });
                });
    }
}
