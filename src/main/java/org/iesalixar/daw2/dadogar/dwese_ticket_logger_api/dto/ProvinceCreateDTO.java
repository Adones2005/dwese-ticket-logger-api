package org.iesalixar.daw2.dadogar.dwese_ticket_logger_api.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProvinceCreateDTO {

    @NotEmpty(message = "{msg.province.code.notEmpty}")
    @Size(max = 2, message ="{msg.province.code.size}" )
    private  String code;

    @NotEmpty(message = "{msg.province.name.notEmpty}")
    @Size(max = 100, message ="{msg.province.name.size}" )
    private String name;
}
