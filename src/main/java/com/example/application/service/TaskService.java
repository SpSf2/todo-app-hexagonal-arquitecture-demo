//Implementa el caso de uso
package com.example.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.application.port.in.CreateTaskUseCase;
import com.example.application.port.in.GetTaskusecase;
import com.example.application.port.in.ListTaskUseCase;
import com.example.application.port.out.TaskRepositoryPort;
import com.example.domain.exception.TaskNotFoundException;
import com.example.domain.model.Task;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor //para que el constructor que se hace a mano sea inyectable
@Service //para que este servicio sea autoinyectable.  ¿Es correcta esta anotación de Spring?
/* Los más puriostas dirian que NO, pero tiene un coste implementar esto correctamente.

Con esta anotacion estamos introduciendo una dependencia del framework en la capa de aplicacion,
y el problema es que si mañana migramos a quarkus o cualquier otro framework ó  si queremos
testear el caso de uso en aislamiento total, esta clase ya no sería agnostica del framework,
es decir, estaría acoplada el spring framework  

¿Que debería hacer para que este acoplamiento no existiera? : Crear una clase de configuración,
anotada con @Configuration o @Componente en la capa de Infraestructura donde tengamos todos los
Bean que hay que crear cuando se levanta el contexto de Spring  */

public class TaskService implements CreateTaskUseCase, GetTaskusecase, ListTaskUseCase {
    //inyectamos el port
    private final TaskRepositoryPort taskRepositoryPort;

    @Override
    public Task create(Task task) {
        return taskRepositoryPort.save(task);
    }

    @Override
    public Task getById(long id) {
        
        return taskRepositoryPort.findById(id)
                      .orElseThrow(() -> new TaskNotFoundException(id));     
    }

    @Override
    public List<Task> listAll() {
       
        return taskRepositoryPort.findAll();
    }

}
