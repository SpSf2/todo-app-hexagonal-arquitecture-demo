package com.example.infrastructure.adapter.in.rest;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.application.port.in.CompleteTaskUseCase;
import com.example.application.port.in.CreateTaskUseCase;
import com.example.application.port.in.DeleteTaskUseCase;
import com.example.application.port.in.GetTaskusecase;
import com.example.application.port.in.ListTaskUseCase;
import com.example.application.port.in.UpdateTaskUseCase;
import com.example.application.port.in.UploadTaskImageUseCase;
import com.example.domain.model.Task;
import com.example.infrastructure.adapter.in.rest.dto.CreateTaskRequest;
import com.example.infrastructure.adapter.in.rest.dto.TaskResponse;
import com.example.infrastructure.adapter.in.rest.dto.UpdateTaskRequest;
import com.example.infrastructure.adapter.in.rest.mapper.TaskRestMapper;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController 
@RequestMapping ("/api/v1/tasks")
@RequiredArgsConstructor 
public class TaskController {

    private final CreateTaskUseCase createTaskUseCase;
    private final GetTaskusecase getTaskUseCase;
    private final ListTaskUseCase listTaskUseCase;
    private final TaskRestMapper taskRestMapper;
    private final UpdateTaskUseCase updateTaskUseCase;
    private final CompleteTaskUseCase completeTaskUseCase;
    private final DeleteTaskUseCase deleteTaskUseCase;
    private final UploadTaskImageUseCase uploadTaskImageUseCase;

    @PostMapping
    public ResponseEntity<TaskResponse> create(@Valid @RequestBody CreateTaskRequest request){
        Task task = taskRestMapper.toDomain(request);
        Task saved = createTaskUseCase.create(task);

        return ResponseEntity.status(HttpStatus.CREATED).body(taskRestMapper.toResponse(saved));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getById(@PathVariable long id){
        Task task = getTaskUseCase.getById(id);
        return ResponseEntity.ok(taskRestMapper.toResponse(task));
    }

    @GetMapping
    public ResponseEntity<List<TaskResponse>> ListAll() {
        List<Task> tasks = listTaskUseCase.listAll();
        return ResponseEntity.ok(taskRestMapper.toResponseList(tasks));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> updateTask(
            @PathVariable Long id,
            @Valid @RequestBody UpdateTaskRequest request) {

        Task updatedTask = updateTaskUseCase.updateTask(id, request.title(), request.description());
        TaskResponse response = taskRestMapper.toResponse(updatedTask);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/complete")
    public ResponseEntity<TaskResponse> completeTask(@PathVariable Long id) {
        Task completedTask = completeTaskUseCase.completeTask(id);
        TaskResponse response = taskRestMapper.toResponse(completedTask);
        
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        deleteTaskUseCase.deleteTask(id);
        return ResponseEntity.noContent().build(); // Retorna 204 No Content
    }

    @PostMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<TaskResponse> uploadImage(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file) {

        Task task = uploadTaskImageUseCase.uploadImage(id, file);
        TaskResponse response = taskRestMapper.toResponse(task);

        return ResponseEntity.ok(response);
    }
}