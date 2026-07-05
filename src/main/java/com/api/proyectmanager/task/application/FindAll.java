package com.api.proyectmanager.task.application;

import java.util.List;

import org.springframework.stereotype.Service;

import com.api.proyectmanager.task.domain.Task;
import com.api.proyectmanager.task.domain.ports.TaskRepository;

@Service("TaskFindAll")
public class FindAll {
    private final TaskRepository taskRepository;

    public FindAll(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> execute() {
        return taskRepository.findAll();
    }
}
