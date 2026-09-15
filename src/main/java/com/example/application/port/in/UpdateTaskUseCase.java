package com.example.application.port.in;

import com.example.domain.model.Task;

/**
 * UpdateTaskUseCase
 */
public interface UpdateTaskUseCase {
    //Método para actualizar una tarea
    Task updateTask(Long id, String title, String description);

}
