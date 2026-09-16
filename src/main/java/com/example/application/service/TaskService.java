//Implementa el caso de uso
package com.example.application.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.application.port.in.CompleteTaskUseCase;
import com.example.application.port.in.CreateTaskUseCase;
import com.example.application.port.in.DeleteTaskUseCase;
import com.example.application.port.in.GetTaskusecase;
import com.example.application.port.in.ListTaskUseCase;
import com.example.application.port.in.UpdateTaskUseCase;
import com.example.application.port.in.UploadTaskImageUseCase;
import com.example.application.port.out.StoragePort;
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

public class TaskService implements CreateTaskUseCase, GetTaskusecase, ListTaskUseCase, UpdateTaskUseCase,
               CompleteTaskUseCase, DeleteTaskUseCase, UploadTaskImageUseCase {
    //inyectamos el port
    private final TaskRepositoryPort taskRepositoryPort;
    private final StoragePort storagePort; // Inyectamos el puerto de almacenamiento

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

    @Override
    public Task updateTask(Long id, String title, String description) {
        Task task = taskRepositoryPort.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Task not found with id: " + id));

        task.updateInfo(title, description);

        return taskRepositoryPort.save(task);
    }

    @Override
    public Task completeTask(Long id) {
        Task task = taskRepositoryPort.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Task not found with id: " + id));

        // Ejecuta la regla de dominio que cambia el status a COMPLETED
        task.complete(); 

        return taskRepositoryPort.save(task);
    }

    @Override
    public Task uploadImage(Long taskId, MultipartFile file) {
        // 1. Buscar la tarea
        Task task = taskRepositoryPort.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + taskId));

        // 2. Si ya tenía una imagen previa, la eliminamos físicamente
        if (task.getImageUrl() != null) {
            storagePort.delete(task.getImageUrl());
        }

        // 3. Guardar el nuevo archivo en disco
        String fileName = storagePort.store(file, taskId);

        // 4. Actualizar el modelo de dominio y persistir
        task.updateImage(fileName);
        return taskRepositoryPort.save(task);
    }

    @Override
    public void deleteTask(Long id) {
        Task task = taskRepositoryPort.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + id));

        // Borrar el archivo de imagen asociado si existe
        if (task.getImageUrl() != null) {
            storagePort.delete(task.getImageUrl());
        }

        taskRepositoryPort.deleteById(task.getId());
    }
}


