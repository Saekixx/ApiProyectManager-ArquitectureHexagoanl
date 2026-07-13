package com.api.proyectmanager.task.application.usecases;

import java.util.List;

import com.api.proyectmanager.shared.domain.annotation.UseCase;
import com.api.proyectmanager.task.domain.Task;
import com.api.proyectmanager.task.domain.ports.TaskRepository;

@UseCase
public class FindAll {
    private final TaskRepository taskRepository;

    public FindAll(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> execute() {
        return taskRepository.findAll();
    }
}
