package com.example.application.port.in;

import com.example.domain.model.Task;

public interface GetTaskusecase {

    //Método para obtener una tarea por su id
    Task getById(long id);

}
