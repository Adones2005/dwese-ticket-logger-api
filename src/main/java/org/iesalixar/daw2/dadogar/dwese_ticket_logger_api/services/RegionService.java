package org.iesalixar.daw2.dadogar.dwese_ticket_logger_api.services;

import org.iesalixar.daw2.dadogar.dwese_ticket_logger_api.dto.RegionCreateDTO;
import org.iesalixar.daw2.dadogar.dwese_ticket_logger_api.dto.RegionDTO;
import org.iesalixar.daw2.dadogar.dwese_ticket_logger_api.entity.Region;
import org.iesalixar.daw2.dadogar.dwese_ticket_logger_api.mappers.RegionMapper;
import org.iesalixar.daw2.dadogar.dwese_ticket_logger_api.repositories.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RegionService {

    @Autowired
    private  RegionRepository regionRepository;

    @Autowired
    private RegionMapper regionMapper;

    public RegionService(RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }

    public List<RegionDTO> getAllRegions() {
        return regionRepository.findAll()
                .stream()
                .map(regionMapper::toDTO)
                .collect(Collectors.toList());
    }

    public RegionDTO getRegionById(Long id) {
        Region region = regionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Región no encontrada con ID: " + id));
        return regionMapper.toDTO(region);
    }

    public RegionDTO createRegion(RegionCreateDTO regionCreateDTO) {
        if (regionRepository.existsByCode(regionCreateDTO.getCode())) {
            throw new IllegalArgumentException("El código de la región ya existe: " + regionCreateDTO.getCode());
        }
        Region region = regionMapper.toEntity(regionCreateDTO);
        Region savedRegion = regionRepository.save(region);
        return regionMapper.toDTO(savedRegion);
    }

    public RegionDTO updateRegion(Long id, RegionCreateDTO regionCreateDTO) {
        Region existingRegion = regionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Región no encontrada con ID: " + id));
        existingRegion.setCode(regionCreateDTO.getCode());
        existingRegion.setName(regionCreateDTO.getName());
        Region updatedRegion = regionRepository.save(existingRegion);
        return regionMapper.toDTO(updatedRegion);
    }

    public void deleteRegion(Long id) {
        if (!regionRepository.existsById(id)) {
            throw new IllegalArgumentException("Región no encontrada con ID: " + id);
        }
        regionRepository.deleteById(id);
    }
}
