package org.iesalixar.daw2.dadogar.dwese_ticket_logger_api.services;

import org.iesalixar.daw2.dadogar.dwese_ticket_logger_api.dto.ProvinceCreateDTO;
import org.iesalixar.daw2.dadogar.dwese_ticket_logger_api.dto.ProvinceDTO;
import org.iesalixar.daw2.dadogar.dwese_ticket_logger_api.entity.Province;
import org.iesalixar.daw2.dadogar.dwese_ticket_logger_api.mappers.ProvinceMapper;
import org.iesalixar.daw2.dadogar.dwese_ticket_logger_api.repositories.ProvinceRepository;
import org.iesalixar.daw2.dadogar.dwese_ticket_logger_api.repositories.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProvinceService {

    @Autowired
    private ProvinceRepository provinceRepository;

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private ProvinceMapper provinceMapper;

    public ProvinceService(ProvinceRepository provinceRepository, RegionRepository regionRepository) {
        this.provinceRepository = provinceRepository;
        this.regionRepository = regionRepository;
    }

    public List<ProvinceDTO> getAllProvinces() {
        return provinceRepository.findAll()
                .stream()
                .map(provinceMapper::toDTO)
                .collect(Collectors.toList());
    }

    public ProvinceDTO getProvinceById(Long id) {
        Province province = provinceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Provincia no encontrada con ID: " + id));
        return provinceMapper.toDTO(province);
    }

    public ProvinceDTO createProvince(ProvinceCreateDTO provinceCreateDTO) {
        if (provinceRepository.existsByCode(provinceCreateDTO.getCode())) {
            throw new IllegalArgumentException("El código de la provincia ya existe: " + provinceCreateDTO.getCode());
        }
        Province province = provinceMapper.toEntity(provinceCreateDTO);
        Province savedProvince = provinceRepository.save(province);
        return provinceMapper.toDTO(savedProvince);
    }

    public ProvinceDTO updateProvince(Long id, ProvinceCreateDTO provinceCreateDTO) {
        Province existingProvince = provinceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Provincia no encontrada con ID: " + id));
        existingProvince.setCode(provinceCreateDTO.getCode());
        existingProvince.setName(provinceCreateDTO.getName());
        Province updatedProvince = provinceRepository.save(existingProvince);
        return provinceMapper.toDTO(updatedProvince);
    }

    public void deleteProvince(Long id) {
        if (!provinceRepository.existsById(id)) {
            throw new IllegalArgumentException("Provincia no encontrada con ID: " + id);
        }
        provinceRepository.deleteById(id);
    }
}
