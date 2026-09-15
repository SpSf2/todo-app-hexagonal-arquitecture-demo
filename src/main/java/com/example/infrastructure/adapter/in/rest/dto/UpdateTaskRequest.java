package com.example.infrastructure.adapter.in.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateTaskRequest(
    @NotBlank(message = "El título no puede estar vacío")
    @Size(max = 100, message = "El título no puede superar los 100 caracteres")
    String title,

    @NotBlank(message = "La descripción no puede estar vacía")
    @Size(max = 255, message = "La descripción no puede superar los 255 caracteres")
    String description
) {}