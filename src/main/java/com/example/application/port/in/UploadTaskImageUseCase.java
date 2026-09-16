package com.example.application.port.in;

import com.example.domain.model.Task;
import org.springframework.web.multipart.MultipartFile;

public interface UploadTaskImageUseCase {
    Task uploadImage(Long taskId, MultipartFile file);
}