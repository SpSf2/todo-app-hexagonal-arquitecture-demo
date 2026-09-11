package com.example.application.port.in;

import com.example.domain.model.Task;

public interface CreateTaskUseCase {
    //Esto es lo que puede crear el dominio, crear una tarea en  este caso
    Task create(Task task);

}
