package org.iesalixar.daw2.dadogar.dwese_ticket_logger_api.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/images")
public class FileStorageController {

    @Value("${UPLOAD_PATH}")
    private String uploadPath;

    private static final Logger logger = LoggerFactory.getLogger(FileStorageController.class);

    @GetMapping("/{fileName}")
    public ResponseEntity<Resource> getImage(@PathVariable String fileName) {
        try {
            // Construir la ruta completa del archivo a partir del directorio de almacenamiento y el nombre del archivo.
            Path filePath = Paths.get(uploadPath).resolve(fileName).normalize();

            // Crear un recurso a partir de la URI del archivo.
            Resource resource = new UrlResource(filePath.toUri());

            // Verifica si el archivo existe y es accesible.
            if (!resource.exists() || !resource.isReadable()) {
                logger.error("El archivo {} no existe o no se puede leer.", fileName);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            // Determinar el tipo MIME del archivo.
            String contentType = Files.probeContentType(filePath);
            if (contentType == null) {
                logger.warn("No se pudo detectar el tipo MIME del archivo {}. Se usará el tipo genérico.", fileName);
                contentType = "application/octet-stream";
            }

            // Devolver el archivo con el tipo MIME y configuración adecuada para mostrarlo en línea.
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, contentType)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);

        } catch (IOException ex) {
            logger.error("Error al servir el archivo {}: {}", fileName, ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
