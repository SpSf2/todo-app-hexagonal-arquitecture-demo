package com.example.domain.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@NoArgsConstructor 
@AllArgsConstructor 
@Getter 
@Setter 
@Builder 
@EqualsAndHashCode(onlyExplicitlyIncluded = true)//para que participen solo los campos que se indiquen con @EqualsAndHashCode.Include
public class Task {

    @EqualsAndHashCode.Include
    private long id;
    private String title;
    private String description;
    private TaskStatus status; //ENUM
    private LocalDateTime createdAt;
    private LocalDateTime completedAt;

    /* Los metodos siguientes aportan comportamiento, , es decir, las reglas de negocio para la
    gestión de las tareas */

    public void complete(){
        if(this.status == TaskStatus.COMPLETED){
            throw new IllegalStateException("Task is already completed");
        }
        this.status = TaskStatus.COMPLETED;
        this.completedAt = LocalDateTime.now();
    }

    public void reopen(){
        if(this.status == TaskStatus.PENDING){
            throw new IllegalStateException("Task is already pending");
        }
        this.status = TaskStatus.PENDING;
        this.completedAt = null;
    }

    public void initDefaults(){
        if (status == null) {
            this.status = TaskStatus.PENDING;
        }

        if (createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
    }

    public void updateInfo(String title, String description) {
        // Si ya se completó, no se deja editar (bloqueo de seguridad)
        if (this.status == TaskStatus.COMPLETED) {
            throw new IllegalStateException("Cannot update a completed task");
        }

        // Validaciones de contenido
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Description cannot be empty");
        }

        // Se actualizan los campos si el status es PENDING
        this.title = title;
        this.description = description;
    }
        //this no es necesario cuando no hay nombres repetidos, 
        // pero es una buena práctica para evitar confusiones
}














