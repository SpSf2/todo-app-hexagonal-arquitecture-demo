package com.example.infrastructure.adapter.out.storage;

import com.example.application.port.out.StoragePort;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Component
public class LocalStorageAdapter implements StoragePort {

    private final Path uploadPath = Paths.get("uploads");

    public LocalStorageAdapter() {
        try {
            // Crea la carpeta 'uploads' en la raíz si no existe
            Files.createDirectories(uploadPath);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize upload folder", e);
        }
    }

    @Override
    public String store(MultipartFile file, Long taskId) {
        try {
            if (file.isEmpty()) {
                throw new IllegalArgumentException("Cannot store empty file");
            }

            // Genera un nombre único para evitar colisiones: uuid-nombreOriginal
            String fileName = taskId + "_" + UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path destination = uploadPath.resolve(fileName);

            Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);

            return fileName; // Retorna el nombre con el que se guardó
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file", e);
        }
    }

    @Override
    public void delete(String fileName) {
        if (fileName == null || fileName.isBlank()) {
            return;
        }
        try {
            Path file = uploadPath.resolve(fileName);
            Files.deleteIfExists(file);
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete file: " + fileName, e);
        }
    }
}
