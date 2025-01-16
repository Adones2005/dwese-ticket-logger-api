package org.iesalixar.daw2.dadogar.dwese_ticket_logger_api.mappers;

import org.iesalixar.daw2.dadogar.dwese_ticket_logger_api.dto.RegionCreateDTO;
import org.iesalixar.daw2.dadogar.dwese_ticket_logger_api.dto.RegionDTO;
import org.iesalixar.daw2.dadogar.dwese_ticket_logger_api.entity.Region;
import org.springframework.stereotype.Component;

@Component
public class RegionMapper {

    /**
     * Convierte una entidad Region en un DTO RegionDTO.
     *
     * @param region Entidad Region.
     * @return RegionDTO correspondiente.
     */
    public RegionDTO toDTO(Region region) {

        RegionDTO dto = new RegionDTO();
        dto.setId(region.getId());
        dto.setCode(region.getCode());
        dto.setName(region.getName());

        return dto;
    }

    public Region toEntity(RegionDTO dto){
        Region region = new Region();
        region.setId(dto.getId());
        region.setCode(dto.getCode());
        region.setName(dto.getName());
        return region;
    }

    /**
     * Convierte un DTO RegionCreateDTO en una entidad Region.
     *
     * @param createDTO DTO con los datos para crear una región.
     * @return Entidad Region correspondiente.
     */
    public  Region toEntity(RegionCreateDTO createDTO) {

        Region region = new Region();
        region.setCode(createDTO.getCode());
        region.setName(createDTO.getName());

        return region;
    }
}
