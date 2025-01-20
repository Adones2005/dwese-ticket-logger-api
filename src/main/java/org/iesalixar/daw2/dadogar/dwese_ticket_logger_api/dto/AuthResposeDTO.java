package org.iesalixar.daw2.dadogar.dwese_ticket_logger_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResposeDTO {
    private String token;
    private String message;
}
