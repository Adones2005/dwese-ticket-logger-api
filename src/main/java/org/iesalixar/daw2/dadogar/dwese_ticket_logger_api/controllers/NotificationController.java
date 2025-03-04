package org.iesalixar.daw2.dadogar.dwese_ticket_logger_api.controllers;

import org.iesalixar.daw2.dadogar.dwese_ticket_logger_api.dto.NotificationCreateDTO;
import org.iesalixar.daw2.dadogar.dwese_ticket_logger_api.dto.NotificationDTO;
import org.iesalixar.daw2.dadogar.dwese_ticket_logger_api.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Controlador REST para gestionar las notificaciones en la aplicación.
 * Proporciona endpoints para obtener y crear notificaciones utilizando WebFlux.
 */
@RestController
@RequestMapping("/ws/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    /**
     * Obtiene todas las notificaciones almacenadas en la base de datos.
     *
     * @return Un Flux con la Lista de todas las notificaciones en formato DTO.
     */
    @GetMapping
    public Flux<NotificationDTO> getAllNotifications() {
        return notificationService.getAllNotifications();
    }

    /**
     * Crea una nueva notificación y la distribuye a los clientes WebSocket.
     *
     * @param notificationCreateDTO Con los datos necesarios para crear la notificación.
     * @return Un Mono con la notificación creada en formato DTO.
     */
    @PostMapping
    public Mono<NotificationDTO> createNotification(@RequestBody NotificationCreateDTO notificationCreateDTO) {
        return notificationService.saveNotification(notificationCreateDTO);
    }
}
