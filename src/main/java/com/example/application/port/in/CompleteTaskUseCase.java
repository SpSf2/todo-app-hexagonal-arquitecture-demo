package com.example.application.port.in;

import com.example.domain.model.Task;

public interface CompleteTaskUseCase {
    Task completeTask(Long id);
}