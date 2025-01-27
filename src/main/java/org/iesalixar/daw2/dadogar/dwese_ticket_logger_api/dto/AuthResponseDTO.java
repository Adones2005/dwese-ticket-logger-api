package org.iesalixar.daw2.dadogar.dwese_ticket_logger_api.dto;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class AuthResponseDTO {
    private String token;
    private String message;
}

