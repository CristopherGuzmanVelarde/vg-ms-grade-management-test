package pe.edu.vallegrande.vg_ms_grade_management.application.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.vallegrande.vg_ms_grade_management.application.service.NotificationService;
import pe.edu.vallegrande.vg_ms_grade_management.domain.model.Notification;
import pe.edu.vallegrande.vg_ms_grade_management.infrastructure.mapper.NotificationMapper;
import pe.edu.vallegrande.vg_ms_grade_management.infrastructure.repository.NotificationRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;

    @Override
    public Mono<Notification> save(Notification notification) {
        notification.setCreatedAt(LocalDateTime.now());
        return notificationRepository.save(notificationMapper.toDocument(notification))
                .map(notificationMapper::toDomain);
    }

    @Override
    public Mono<Notification> findById(String id) {
        return notificationRepository.findById(id)
                .map(notificationMapper::toDomain);
    }

    @Override
    public Flux<Notification> findAll() {
        return notificationRepository.findAll()
                .filter(document -> !Boolean.TRUE.equals(document.getDeleted()))
                .map(notificationMapper::toDomain);
    }

    @Override
    public Mono<Notification> update(String id, Notification notification) {
        return notificationRepository.findById(id)
                .flatMap(existingNotificationDocument -> {
                    existingNotificationDocument.setRecipientId(notification.getRecipientId());
                    existingNotificationDocument.setRecipientType(notification.getRecipientType());
                    existingNotificationDocument.setMessage(notification.getMessage());
                    existingNotificationDocument.setNotificationType(notification.getNotificationType());
                    existingNotificationDocument.setStatus(notification.getStatus());
                    existingNotificationDocument.setChannel(notification.getChannel());
                    existingNotificationDocument.setSentAt(notification.getSentAt());
                    return notificationRepository.save(existingNotificationDocument);
                })
                .map(notificationMapper::toDomain);
    }

    @Override
    public Mono<Notification> deleteById(String id) {
        return notificationRepository.findById(id)
                .flatMap(document -> {
                    document.setDeleted(true);
                    return notificationRepository.save(document);
                })
                .map(notificationMapper::toDomain);
    }

    @Override
    public Mono<Notification> restoreById(String id) {
        return notificationRepository.findById(id)
                .flatMap(document -> {
                    document.setDeleted(false);
                    return notificationRepository.save(document);
                })
                .map(notificationMapper::toDomain);
    }
}