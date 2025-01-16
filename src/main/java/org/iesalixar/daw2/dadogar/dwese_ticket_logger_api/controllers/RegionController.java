package org.iesalixar.daw2.dadogar.dwese_ticket_logger_api.controllers;

import org.iesalixar.daw2.dadogar.dwese_ticket_logger_api.dto.RegionCreateDTO;
import org.iesalixar.daw2.dadogar.dwese_ticket_logger_api.dto.RegionDTO;
import org.iesalixar.daw2.dadogar.dwese_ticket_logger_api.services.RegionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/regions")
public class RegionController {

    private final RegionService regionService;

    public RegionController(RegionService regionService) {
        this.regionService = regionService;
    }

    @GetMapping
    public ResponseEntity<?> getAllRegions() {
        try {
            List<RegionDTO> regions = regionService.getAllRegions();
            return ResponseEntity.ok(regions);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al obtener las regiones.");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRegionById(@PathVariable Long id) {
        try {
            RegionDTO region = regionService.getRegionById(id);
            return ResponseEntity.ok(region);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al obtener la región.");
        }
    }

    @PostMapping
    public ResponseEntity<?> createRegion(@RequestBody RegionCreateDTO regionCreateDTO) {
        try {
            RegionDTO createdRegion = regionService.createRegion(regionCreateDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdRegion);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear la región.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRegion(@PathVariable Long id, @RequestBody RegionCreateDTO regionCreateDTO) {
        try {
            RegionDTO updatedRegion = regionService.updateRegion(id, regionCreateDTO);
            return ResponseEntity.ok(updatedRegion);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar la región.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRegion(@PathVariable Long id) {
        try {
            regionService.deleteRegion(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar la región.");
        }
    }
}
