package pe.edu.vallegrande.vg_ms_grade_management.application.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.vallegrande.vg_ms_grade_management.application.service.GradeNotificationService;
import pe.edu.vallegrande.vg_ms_grade_management.application.service.NotificationService;
import pe.edu.vallegrande.vg_ms_grade_management.infrastructure.client.StudentClient;
import pe.edu.vallegrande.vg_ms_grade_management.application.service.CourseService;
import pe.edu.vallegrande.vg_ms_grade_management.domain.model.Grade;
import pe.edu.vallegrande.vg_ms_grade_management.domain.model.Notification;
import pe.edu.vallegrande.vg_ms_grade_management.domain.model.Course;
import reactor.core.publisher.Mono;
import pe.edu.vallegrande.vg_ms_grade_management.infrastructure.dto.response.StudentDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
@RequiredArgsConstructor
public class GradeNotificationServiceImpl implements GradeNotificationService {

    private static final Logger logger = LoggerFactory.getLogger(GradeNotificationServiceImpl.class);
    private final NotificationService notificationService;
    private final StudentClient studentClient;
    private final CourseService courseService;

    @Override
    public Mono<Notification> generateGradePublishedNotification(Grade grade) {
        logger.info("Generating grade published notification for grade: {}", grade);
        logger.info("Student ID: {}, Course ID: {}", grade.getStudentId(), grade.getCourseId());
        
        return Mono.zip(
                studentClient.getStudentById(grade.getStudentId())
                        .doOnNext(student -> logger.info("Successfully retrieved student: {}", student))
                        .onErrorResume(e -> {
                            logger.error("Error al obtener datos del estudiante desde el microservicio: ", e);
                            return Mono.error(new RuntimeException("No se pudo obtener la información del estudiante", e));
                        }),
                courseService.findById(grade.getCourseId())
                        .doOnNext(course -> logger.info("Successfully retrieved course: {}", course))
                        .onErrorResume(e -> {
                            logger.warn("Error al obtener datos del curso, usando courseId: {}", grade.getCourseId());
                            return Mono.just(createDefaultCourse(grade.getCourseId()));
                        })
                        .defaultIfEmpty(createDefaultCourse(grade.getCourseId()))
        ).flatMap(tuple -> {
            StudentDTO student = tuple.getT1();
            Course course = tuple.getT2();
            
            String nombreEstudiante = safeConcat(student.getFirstName(), student.getLastName());
            String nombreCurso = course != null ? course.getName() : "Curso " + grade.getCourseId().substring(0, Math.min(8, grade.getCourseId().length()));
            
            String mensaje = String.format(
                "Hola %s, tu calificación de %s ha sido publicada. Obtuviste %.1f/20 puntos.",
                nombreEstudiante,
                nombreCurso,
                grade.getGrade()
            );
            
            Notification notification = Notification.builder()
                    .recipientId(grade.getStudentId())
                    .recipientType(nombreEstudiante)
                    .message(mensaje)
                    .notificationType("Calificación Publicada")
                    .status("Pendiente")
                    .channel("Correo")
                    .build();
                    
            logger.info("Intentando guardar notificación: {}", notification);
            return notificationService.save(notification)
                .doOnSuccess(n -> logger.info("Notificación guardada exitosamente: {}", n))
                .doOnError(e -> logger.error("Error al guardar la notificación: ", e));
        }).onErrorResume(e -> {
            logger.error("Error completo en generateGradePublishedNotification: ", e);
            return Mono.error(new RuntimeException("Error al generar notificación de calificación publicada", e));
        });
    }

    @Override
    public Mono<Notification> generateGradeUpdatedNotification(Grade grade) {
        logger.info("Generating grade updated notification for grade: {}", grade);
        logger.info("Student ID: {}, Course ID: {}", grade.getStudentId(), grade.getCourseId());
        
        return Mono.zip(
                studentClient.getStudentById(grade.getStudentId())
                        .doOnNext(student -> logger.info("Successfully retrieved student: {}", student))
                        .onErrorResume(e -> {
                            logger.error("Error al obtener datos del estudiante desde el microservicio: ", e);
                            return Mono.error(new RuntimeException("No se pudo obtener la información del estudiante", e));
                        }),
                courseService.findById(grade.getCourseId())
                        .doOnNext(course -> logger.info("Successfully retrieved course: {}", course))
                        .onErrorResume(e -> {
                            logger.warn("Error al obtener datos del curso, usando courseId: {}", grade.getCourseId());
                            return Mono.just(createDefaultCourse(grade.getCourseId()));
                        })
                        .defaultIfEmpty(createDefaultCourse(grade.getCourseId()))
        ).flatMap(tuple -> {
            StudentDTO student = tuple.getT1();
            Course course = tuple.getT2();
            
            String nombreEstudiante = safeConcat(student.getFirstName(), student.getLastName());
            String nombreCurso = course != null ? course.getName() : "Curso " + grade.getCourseId().substring(0, Math.min(8, grade.getCourseId().length()));
            
            String mensaje = String.format(
                "Hola %s, tu calificación de %s ha sido actualizada. Nueva calificación: %.1f/20 puntos.",
                nombreEstudiante,
                nombreCurso,
                grade.getGrade()
            );
            
            Notification notification = Notification.builder()
                    .recipientId(grade.getStudentId())
                    .recipientType(nombreEstudiante)
                    .message(mensaje)
                    .notificationType("Calificación Actualizada")
                    .status("Pendiente")
                    .channel("Correo")
                    .build();
                    
            logger.info("Intentando guardar notificación: {}", notification);
            return notificationService.save(notification)
                .doOnSuccess(n -> logger.info("Notificación guardada exitosamente: {}", n))
                .doOnError(e -> logger.error("Error al guardar la notificación: ", e));
        }).onErrorResume(e -> {
            logger.error("Error completo en generateGradeUpdatedNotification: ", e);
            return Mono.error(new RuntimeException("Error al generar notificación de calificación actualizada", e));
        });
    }

    @Override
    public Mono<Notification> generateLowPerformanceNotification(Grade grade) {
        // Mantener la lógica de parent sin aplicar (dejar el ID como está)
        return courseService.findById(grade.getCourseId())
            .flatMap(course -> {
                String nombreCurso = course != null ? course.getName() : grade.getCourseId();
                String mensaje = String.format(
                    "Su hijo/a ha obtenido una calificación baja (%.1f/20) en %s. Por favor revise el progreso académico.",
                    grade.getGrade(),
                    nombreCurso
                );
                Notification notification = Notification.builder()
                        .recipientId(getParentId(grade.getStudentId())) // Mantener el ID como está
                        .recipientType("PARENT")
                        .message(mensaje)
                        .notificationType("Bajo Rendimiento")
                        .status("Pendiente")
                        .channel("SMS")
                        .build();
                return notificationService.save(notification);
            });
    }

    @Override
    public Mono<Grade> processGradeNotifications(Grade grade, boolean isNewGrade) {
        return Mono.just(grade)
                .flatMap(savedGrade -> {
                    // Generar notificación principal
                    Mono<Notification> mainNotification = isNewGrade 
                        ? generateGradePublishedNotification(savedGrade)
                        : generateGradeUpdatedNotification(savedGrade);
                    
                    return mainNotification.thenReturn(savedGrade);
                })
                .flatMap(savedGrade -> {
                    // Verificar si es bajo rendimiento y generar notificación adicional
                    if (savedGrade.getGrade() != null && savedGrade.getGrade() < 11.0) {
                        return generateLowPerformanceNotification(savedGrade)
                                .thenReturn(savedGrade);
                    }
                    return Mono.just(savedGrade);
                });
    }

    /**
     * Obtiene el ID del padre basado en el ID del estudiante
     * En una implementación real, esto consultaría el servicio de estudiantes
     */
    private String getParentId(String studentId) {
        // Por ahora retornamos un ID genérico
        // En una implementación real, esto consultaría StudentService
        return "P" + studentId;
    }

    // Método utilitario para concatenar nombres de forma segura
    private String safeConcat(String first, String last) {
        if (first == null && last == null) return "";
        if (first == null) return last;
        if (last == null) return first;
        return first + " " + last;
    }
    
    // Método helper para crear un Course por defecto cuando el servicio no está disponible
    private Course createDefaultCourse(String courseId) {
        return Course.builder()
                .id(courseId)
                .name("Curso " + courseId.substring(0, Math.min(8, courseId.length())))
                .teacher("Profesor")
                .deleted(false)
                .build();
    }
}