package com.example.application.port.out;

import java.util.List;
import java.util.Optional;

import com.example.domain.model.Task;

public interface TaskRepositoryPort {

    Task save(Task task);

    //Método para obtener una tarea por su id que coincide con el GetTaskUseCase
    Optional<Task> findById(long id);

    //Método para listar todas las tareas
    List<Task> findAll();

}
