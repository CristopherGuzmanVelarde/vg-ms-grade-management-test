package pe.edu.vallegrande.vg_ms_grade_management.infrastructure.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.vallegrande.vg_ms_grade_management.application.service.NotificationService;
import pe.edu.vallegrande.vg_ms_grade_management.domain.model.Notification;
import pe.edu.vallegrande.vg_ms_grade_management.infrastructure.dto.request.NotificationRequest;
import pe.edu.vallegrande.vg_ms_grade_management.infrastructure.dto.response.NotificationResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationRest {

    private final NotificationService notificationService;

    @PostMapping
    public Mono<ResponseEntity<NotificationResponse>> createNotification(@RequestBody NotificationRequest request) {
        Notification notification = Notification.builder()
                .recipientId(request.getRecipientId())
                .recipientType(request.getRecipientType())
                .message(request.getMessage())
                .notificationType(request.getNotificationType())
                .status(request.getStatus())
                .channel(request.getChannel())
                .build();
        return notificationService.save(notification)
                .map(savedNotification -> {
                    NotificationResponse response = new NotificationResponse();
                    response.setId(savedNotification.getId());
                    response.setRecipientId(savedNotification.getRecipientId());
                    response.setRecipientType(savedNotification.getRecipientType());
                    response.setMessage(savedNotification.getMessage());
                    response.setNotificationType(savedNotification.getNotificationType());
                    response.setStatus(savedNotification.getStatus());
                    response.setChannel(savedNotification.getChannel());
                    response.setCreatedAt(savedNotification.getCreatedAt());
                    response.setSentAt(savedNotification.getSentAt());
                    return new ResponseEntity<>(response, HttpStatus.CREATED);
                });
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<NotificationResponse>> getNotificationById(@PathVariable String id) {
        return notificationService.findById(id)
                .map(notification -> {
                    NotificationResponse response = new NotificationResponse();
                    response.setId(notification.getId());
                    response.setRecipientId(notification.getRecipientId());
                    response.setRecipientType(notification.getRecipientType());
                    response.setMessage(notification.getMessage());
                    response.setNotificationType(notification.getNotificationType());
                    response.setStatus(notification.getStatus());
                    response.setChannel(notification.getChannel());
                    response.setCreatedAt(notification.getCreatedAt());
                    response.setSentAt(notification.getSentAt());
                    return new ResponseEntity<>(response, HttpStatus.OK);
                })
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public Flux<NotificationResponse> getAllNotifications() {
        return notificationService.findAll()
                .map(notification -> {
                    NotificationResponse response = new NotificationResponse();
                    response.setId(notification.getId());
                    response.setRecipientId(notification.getRecipientId());
                    response.setRecipientType(notification.getRecipientType());
                    response.setMessage(notification.getMessage());
                    response.setNotificationType(notification.getNotificationType());
                    response.setStatus(notification.getStatus());
                    response.setChannel(notification.getChannel());
                    response.setCreatedAt(notification.getCreatedAt());
                    response.setSentAt(notification.getSentAt());
                    return response;
                });
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<NotificationResponse>> updateNotification(@PathVariable String id, @RequestBody NotificationRequest request) {
        Notification notification = Notification.builder()
                .recipientId(request.getRecipientId())
                .recipientType(request.getRecipientType())
                .message(request.getMessage())
                .notificationType(request.getNotificationType())
                .status(request.getStatus())
                .channel(request.getChannel())
                .build();
        return notificationService.update(id, notification)
                .map(updatedNotification -> {
                    NotificationResponse response = new NotificationResponse();
                    response.setId(updatedNotification.getId());
                    response.setRecipientId(updatedNotification.getRecipientId());
                    response.setRecipientType(updatedNotification.getRecipientType());
                    response.setMessage(updatedNotification.getMessage());
                    response.setNotificationType(updatedNotification.getNotificationType());
                    response.setStatus(updatedNotification.getStatus());
                    response.setChannel(updatedNotification.getChannel());
                    response.setCreatedAt(updatedNotification.getCreatedAt());
                    response.setSentAt(updatedNotification.getSentAt());
                    return new ResponseEntity<>(response, HttpStatus.OK);
                })
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<NotificationResponse>> deleteNotification(@PathVariable String id) {
        return notificationService.deleteById(id)
                .map(notification -> {
                    NotificationResponse response = new NotificationResponse();
                    response.setId(notification.getId());
                    response.setRecipientId(notification.getRecipientId());
                    response.setRecipientType(notification.getRecipientType());
                    response.setMessage(notification.getMessage());
                    response.setNotificationType(notification.getNotificationType());
                    response.setStatus(notification.getStatus());
                    response.setChannel(notification.getChannel());
                    response.setCreatedAt(notification.getCreatedAt());
                    response.setSentAt(notification.getSentAt());
                    response.setDeleted(notification.getDeleted());
                    return new ResponseEntity<>(response, HttpStatus.OK);
                })
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}/restore")
    public Mono<ResponseEntity<NotificationResponse>> restoreNotification(@PathVariable String id) {
        return notificationService.restoreById(id)
                .map(notification -> {
                    NotificationResponse response = new NotificationResponse();
                    response.setId(notification.getId());
                    response.setRecipientId(notification.getRecipientId());
                    response.setRecipientType(notification.getRecipientType());
                    response.setMessage(notification.getMessage());
                    response.setNotificationType(notification.getNotificationType());
                    response.setStatus(notification.getStatus());
                    response.setChannel(notification.getChannel());
                    response.setCreatedAt(notification.getCreatedAt());
                    response.setSentAt(notification.getSentAt());
                    response.setDeleted(notification.getDeleted());
                    return new ResponseEntity<>(response, HttpStatus.OK);
                })
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}