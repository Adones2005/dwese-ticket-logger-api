package org.iesalixar.daw2.dadogar.dwese_ticket_logger_api.repositories;


import org.iesalixar.daw2.dadogar.dwese_ticket_logger_api.entity.Notification;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

/**
 * Repositorio reactivo para gestionar las notificaciones en MongoDB.
 * Extiende ReactiveMongoRepository para aprovechar la programación reactiva.
 */
public interface NotificationRepository extends ReactiveMongoRepository<Notification, String> {
}

