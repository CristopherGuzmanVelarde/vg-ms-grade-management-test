package pe.edu.vallegrande.vg_ms_grade_management.application.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.vallegrande.vg_ms_grade_management.application.service.GradeNotificationService;
import pe.edu.vallegrande.vg_ms_grade_management.application.service.NotificationService;
import pe.edu.vallegrande.vg_ms_grade_management.application.service.StudentService;
import pe.edu.vallegrande.vg_ms_grade_management.application.service.CourseService;
import pe.edu.vallegrande.vg_ms_grade_management.domain.model.Grade;
import pe.edu.vallegrande.vg_ms_grade_management.domain.model.Notification;
import pe.edu.vallegrande.vg_ms_grade_management.domain.model.Student;
import pe.edu.vallegrande.vg_ms_grade_management.domain.model.Course;
import reactor.core.publisher.Mono;

/**
 * Implementación del servicio de notificaciones transaccionales para calificaciones
 */
@Service
@RequiredArgsConstructor
public class GradeNotificationServiceImpl implements GradeNotificationService {

    private final NotificationService notificationService;
    private final StudentService studentService;
    private final CourseService courseService;

    @Override
    public Mono<Notification> generateGradePublishedNotification(Grade grade) {
        return Mono.zip(
                studentService.findById(Long.valueOf(grade.getStudentId())),
                courseService.findById(grade.getCourseId())
        ).flatMap(tuple -> {
            Student student = tuple.getT1();
            Course course = tuple.getT2();
            String nombreEstudiante = student != null ? student.getFirstName() + " " + student.getLastName() : grade.getStudentId();
            String nombreCurso = course != null ? course.getName() : grade.getCourseId();
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
            return notificationService.save(notification);
        });
    }

    @Override
    public Mono<Notification> generateGradeUpdatedNotification(Grade grade) {
        return Mono.zip(
                studentService.findById(Long.valueOf(grade.getStudentId())),
                courseService.findById(grade.getCourseId())
        ).flatMap(tuple -> {
            Student student = tuple.getT1();
            Course course = tuple.getT2();
            String nombreEstudiante = student != null ? student.getFirstName() + " " + student.getLastName() : grade.getStudentId();
            String nombreCurso = course != null ? course.getName() : grade.getCourseId();
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
            return notificationService.save(notification);
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
} 