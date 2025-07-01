package pe.edu.vallegrande.vg_ms_grade_management.infrastructure.dto.request;

import lombok.Data;

@Data
public class NotificationRequest {
    private String recipientId;
    private String recipientType;
    private String message;
    private String notificationType;
    private String status;
    private String channel;
}