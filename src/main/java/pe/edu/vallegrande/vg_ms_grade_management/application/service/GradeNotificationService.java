package pe.edu.vallegrande.vg_ms_grade_management.application.service;

import pe.edu.vallegrande.vg_ms_grade_management.domain.model.Grade;
import pe.edu.vallegrande.vg_ms_grade_management.domain.model.Notification;
import reactor.core.publisher.Mono;

/**
 * Servicio para manejar notificaciones transaccionales relacionadas con calificaciones
 */
public interface GradeNotificationService {
    
    /**
     * Genera notificaciones automáticas cuando se crea una nueva calificación
     * @param grade Calificación creada
     * @return Mono con la notificación generada
     */
    Mono<Notification> generateGradePublishedNotification(Grade grade);
    
    /**
     * Genera notificaciones automáticas cuando se actualiza una calificación
     * @param grade Calificación actualizada
     * @return Mono con la notificación generada
     */
    Mono<Notification> generateGradeUpdatedNotification(Grade grade);
    
    /**
     * Genera notificaciones de bajo rendimiento cuando la calificación es menor a 11
     * @param grade Calificación con bajo rendimiento
     * @return Mono con la notificación generada
     */
    Mono<Notification> generateLowPerformanceNotification(Grade grade);
    
    /**
     * Procesa todas las notificaciones necesarias para una calificación
     * @param grade Calificación procesada
     * @param isNewGrade Indica si es una calificación nueva o actualizada
     * @return Mono con la calificación procesada
     */
    Mono<Grade> processGradeNotifications(Grade grade, boolean isNewGrade);
} 