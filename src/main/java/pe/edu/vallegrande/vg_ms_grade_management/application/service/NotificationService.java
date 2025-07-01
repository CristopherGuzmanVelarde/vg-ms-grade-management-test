package pe.edu.vallegrande.vg_ms_grade_management.application.service;

import pe.edu.vallegrande.vg_ms_grade_management.domain.model.Notification;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface NotificationService {

    Mono<Notification> save(Notification notification);

    Mono<Notification> findById(String id);

    Flux<Notification> findAll();

    Mono<Notification> update(String id, Notification notification);

    Mono<Notification> deleteById(String id);

    Mono<Notification> restoreById(String id);
}