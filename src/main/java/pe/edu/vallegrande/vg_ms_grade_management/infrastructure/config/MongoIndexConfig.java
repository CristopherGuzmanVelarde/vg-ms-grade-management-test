package pe.edu.vallegrande.vg_ms_grade_management.infrastructure.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.stereotype.Component;

/**
 * Configuración de índices para MongoDB
 * Optimiza las consultas más frecuentes del microservicio
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MongoIndexConfig {

    private final ReactiveMongoTemplate mongoTemplate;

    @PostConstruct
    public void initIndexes() {
        log.info("Iniciando creación de índices en MongoDB...");
        
        var indexOps = mongoTemplate.indexOps("student_grades");
        
        // Índice compuesto: studentId + periodId + typePeriod + courseId
        // Para consultas de calificaciones de un estudiante en un período específico
        Index studentPeriodIndex = new Index()
                .on("studentId", Sort.Direction.ASC)
                .on("periodId", Sort.Direction.ASC)
                .on("typePeriod", Sort.Direction.ASC)
                .on("courseId", Sort.Direction.ASC)
                .named("idx_student_period");
        indexOps.ensureIndex(studentPeriodIndex);
        log.info("Índice creado: idx_student_period");
        
        // Índice compuesto: teacherId + classroomId + periodId + typePeriod
        // Para consultas de calificaciones por teacher en un aula y período específico
        Index teacherClassroomIndex = new Index()
                .on("teacherId", Sort.Direction.ASC)
                .on("classroomId", Sort.Direction.ASC)
                .on("periodId", Sort.Direction.ASC)
                .on("typePeriod", Sort.Direction.ASC)
                .named("idx_teacher_classroom");
        indexOps.ensureIndex(teacherClassroomIndex);
        log.info("Índice creado: idx_teacher_classroom");
        
        // Índice simple: teacherId
        // Para consultas rápidas de todas las calificaciones de un teacher
        Index teacherIndex = new Index()
                .on("teacherId", Sort.Direction.ASC)
                .named("idx_teacher");
        indexOps.ensureIndex(teacherIndex);
        log.info("Índice creado: idx_teacher");
        
        // Índice simple: status
        // Para filtrar calificaciones activas vs modificadas
        Index statusIndex = new Index()
                .on("status", Sort.Direction.ASC)
                .named("idx_status");
        indexOps.ensureIndex(statusIndex);
        log.info("Índice creado: idx_status");
        
        // Índice compuesto: studentId + teacherId
        // Para consultas de historial de estudiante por teacher
        Index studentTeacherIndex = new Index()
                .on("studentId", Sort.Direction.ASC)
                .on("teacherId", Sort.Direction.ASC)
                .named("idx_student_teacher");
        indexOps.ensureIndex(studentTeacherIndex);
        log.info("Índice creado: idx_student_teacher");
        
        // Índice compuesto: classroomId + periodId + typePeriod + courseId
        // Para reportes consolidados por aula y período
        Index classroomPeriodIndex = new Index()
                .on("classroomId", Sort.Direction.ASC)
                .on("periodId", Sort.Direction.ASC)
                .on("typePeriod", Sort.Direction.ASC)
                .on("courseId", Sort.Direction.ASC)
                .named("idx_classroom_period_course");
        indexOps.ensureIndex(classroomPeriodIndex);
        log.info("Índice creado: idx_classroom_period_course");
        
        // Índice para evaluationDate
        // Para ordenar y filtrar por fecha de evaluación
        Index dateIndex = new Index()
                .on("evaluationDate", Sort.Direction.DESC)
                .named("idx_evaluation_date");
        indexOps.ensureIndex(dateIndex);
        log.info("Índice creado: idx_evaluation_date");
        
        log.info("Todos los índices de MongoDB han sido creados exitosamente");
    }
}
