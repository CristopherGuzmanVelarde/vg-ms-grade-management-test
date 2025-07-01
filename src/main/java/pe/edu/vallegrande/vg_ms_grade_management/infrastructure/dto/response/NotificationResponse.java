package pe.edu.vallegrande.vg_ms_grade_management.infrastructure.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NotificationResponse {
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

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}