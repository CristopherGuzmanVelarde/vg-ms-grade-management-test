package pe.edu.vallegrande.vg_ms_grade_management.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "notifications")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    @Id
    private String id;
    private String recipientId;
    private String recipientType; // STUDENT, TEACHER, PARENT
    private String message;
    private String notificationType; // GRADE_PUBLISHED, GRADE_UPDATED, LOW_PERFORMANCE
    private String status; // PENDING, SENT, FAILED
    private String channel; // EMAIL, SMS, PUSH
    private LocalDateTime createdAt;
    private LocalDateTime sentAt;
    private Boolean deleted;
}