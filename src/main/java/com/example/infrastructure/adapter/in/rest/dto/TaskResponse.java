package com.example.infrastructure.adapter.in.rest.dto;

import java.time.LocalDateTime;
import com.example.domain.model.TaskStatus;
import lombok.Builder;

@Builder
public record TaskResponse(
    Long id,
    String title,
    String description,
    TaskStatus status,
    LocalDateTime createdAt,
    LocalDateTime completedAt
) {}