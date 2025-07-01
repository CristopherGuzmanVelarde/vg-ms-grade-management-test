package pe.edu.vallegrande.vg_ms_grade_management.infrastructure.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.edu.vallegrande.vg_ms_grade_management.domain.model.Notification;
import reactor.core.publisher.Mono;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "notifications")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDocument {
    @Id
    private String id;
    private String recipientId;
    private String recipientType;
    private String message;
    private String notificationType;
    private String status;
    private String channel;
    private LocalDateTime createdAt;
    private LocalDateTime sentAt;
    private Boolean deleted;
    public Mono<Notification> map(Object object) {
        throw new UnsupportedOperationException("Unimplemented method 'map'");
    }
}