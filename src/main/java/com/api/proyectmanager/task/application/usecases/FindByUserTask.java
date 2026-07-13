package com.api.proyectmanager.task.application.usecases;

import java.util.List;

import org.springframework.stereotype.Service;

import com.api.proyectmanager.task.domain.Task;
import com.api.proyectmanager.task.domain.ports.TaskRepository;

@Service
public class FindByUserTask {
    private final TaskRepository taskRepository;

    public FindByUserTask(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> execute(Integer userId) {
        // Obtener las tareas asignadas al usuario
        return taskRepository.findByAssignedUserId(userId);
    }
}
