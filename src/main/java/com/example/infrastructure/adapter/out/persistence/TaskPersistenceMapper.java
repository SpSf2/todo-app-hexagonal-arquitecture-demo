package com.example.infrastructure.adapter.out.persistence;

import org.springframework.stereotype.Component;

import com.example.domain.model.Task;

@Component //para que se cree un bean con los metodos de mapeo
public class TaskPersistenceMapper {
    //metodo que convierte de entidad a dominio

    public Task toDomain(TaskJpaEntity taskJpaEntity) {

        if (taskJpaEntity == null)
            return null;
        
        return Task.builder()
                .id(taskJpaEntity.getId())
                .title(taskJpaEntity.getTitle())
                .description(taskJpaEntity.getDescription())
                .status(taskJpaEntity.getStatus())
                .createdAt(taskJpaEntity.getCreatedAt())
                .completedAt(taskJpaEntity.getCompletedAt())
                .build();
        
    }

    //metodo que convierte de dominio a entidad
    public TaskJpaEntity toJpaEntity(Task task) {
        if (task == null)
            return null;
        
        return TaskJpaEntity.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .createdAt(task.getCreatedAt())
                .completedAt(task.getCompletedAt())
                .build();
    }

}
