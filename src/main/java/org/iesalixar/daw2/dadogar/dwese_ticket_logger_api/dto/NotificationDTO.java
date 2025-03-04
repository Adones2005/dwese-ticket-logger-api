package org.iesalixar.daw2.dadogar.dwese_ticket_logger_api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

/**
 * Data Transfer Object (DTO) para la entidad Notification.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDTO {
    private String id;
    private String subject;
    private String message;
    private boolean read;
    private Instant createdAt;
}
