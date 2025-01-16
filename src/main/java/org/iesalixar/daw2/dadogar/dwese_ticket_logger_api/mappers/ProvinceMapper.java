package org.iesalixar.daw2.dadogar.dwese_ticket_logger_api.mappers;

import org.iesalixar.daw2.dadogar.dwese_ticket_logger_api.dto.ProvinceCreateDTO;
import org.iesalixar.daw2.dadogar.dwese_ticket_logger_api.dto.ProvinceDTO;
import org.iesalixar.daw2.dadogar.dwese_ticket_logger_api.entity.Province;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProvinceMapper {

    @Autowired
    RegionMapper regionMapper;
        /**
     * Convierte una entidad Province en un DTO ProvinceDTO.
     *
     * @param province Entidad Province.
     * @return ProvinceDTO correspondiente.
     */
    public ProvinceDTO toDTO(Province province) {
        ProvinceDTO dto = new ProvinceDTO();
        dto.setId(province.getId());
        dto.setCode(province.getCode());
        dto.setName(province.getName());
        dto.setRegion(regionMapper.toDTO(province.getRegion()));

        return dto;
    }


    /**
     * Convierte un DTO ProvinceCreateDTO en una entidad Province.
     *
     * @param createDTO DTO con los datos para crear una provincia.
     * @return Entidad Province correspondiente.
     */
    public Province toEntity(ProvinceCreateDTO createDTO) {
        Province province = new Province();
        province.setCode(createDTO.getCode());
        province.setName(createDTO.getName());
        return province;
    }
}
