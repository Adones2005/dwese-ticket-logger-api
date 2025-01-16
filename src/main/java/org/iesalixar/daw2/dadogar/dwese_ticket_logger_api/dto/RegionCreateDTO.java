package org.iesalixar.daw2.dadogar.dwese_ticket_logger_api.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegionCreateDTO {
    @NotEmpty(message = "{msg.region.code.notEmpty}")
    @Size(max = 2, message ="{msg.region.code.size}" )
    private  String code;

    @NotEmpty(message = "{msg.region.name.notEmpty}")
    @Size(max = 100, message ="{msg.region.name.size}" )
    private String name;
}

