package pe.edu.vallegrande.vg_ms_grade_management.infrastructure.mapper;

import org.mapstruct.Mapper;
import pe.edu.vallegrande.vg_ms_grade_management.domain.model.Notification;
import pe.edu.vallegrande.vg_ms_grade_management.infrastructure.document.NotificationDocument;

@Mapper(componentModel = "spring")
public interface NotificationMapper {

    Notification toDomain(NotificationDocument document);

    NotificationDocument toDocument(Notification domain);
}