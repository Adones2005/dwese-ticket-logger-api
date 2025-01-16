package org.iesalixar.daw2.dadogar.dwese_ticket_logger_api.controllers;

import org.iesalixar.daw2.dadogar.dwese_ticket_logger_api.dto.ProvinceCreateDTO;
import org.iesalixar.daw2.dadogar.dwese_ticket_logger_api.dto.ProvinceDTO;
import org.iesalixar.daw2.dadogar.dwese_ticket_logger_api.services.ProvinceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/provinces")
public class ProvinceController {

    private final ProvinceService provinceService;

    public ProvinceController(ProvinceService provinceService) {
        this.provinceService = provinceService;
    }

    @GetMapping
    public ResponseEntity<?> getAllProvinces() {
        try {
            List<ProvinceDTO> provinces = provinceService.getAllProvinces();
            return ResponseEntity.ok(provinces);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al obtener las provincias.");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProvinceById(@PathVariable Long id) {
        try {
            ProvinceDTO province = provinceService.getProvinceById(id);
            return ResponseEntity.ok(province);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al obtener la provincia.");
        }
    }

    @PostMapping
    public ResponseEntity<?> createProvince(@RequestBody ProvinceCreateDTO provinceCreateDTO) {
        try {
            ProvinceDTO createdProvince = provinceService.createProvince(provinceCreateDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdProvince);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear la provincia.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProvince(@PathVariable Long id, @RequestBody ProvinceCreateDTO provinceCreateDTO) {
        try {
            ProvinceDTO updatedProvince = provinceService.updateProvince(id, provinceCreateDTO);
            return ResponseEntity.ok(updatedProvince);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar la provincia.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProvince(@PathVariable Long id) {
        try {
            provinceService.deleteProvince(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar la provincia.");
        }
    }
}
