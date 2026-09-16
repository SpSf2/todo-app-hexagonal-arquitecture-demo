package com.example.infrastructure.config;

import com.example.application.port.out.StoragePort;
import com.example.application.port.out.TaskRepositoryPort;
import com.example.application.service.TaskService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public TaskService taskService(TaskRepositoryPort taskRepositoryPort, StoragePort storagePort) {
        return new TaskService(taskRepositoryPort, storagePort);
    }
}
