package com.example.application.port.out;

import org.springframework.web.multipart.MultipartFile;

public interface StoragePort {
    /**
     * Guarda el archivo recibido y retorna el nombre o URL generada.
     */
    String store(MultipartFile file, Long taskId);

    /**
     * Elimina el archivo físico del disco dado su nombre o ruta.
     */
    void delete(String fileName);
}
