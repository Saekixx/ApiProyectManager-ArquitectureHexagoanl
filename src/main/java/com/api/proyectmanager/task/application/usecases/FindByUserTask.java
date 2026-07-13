package com.api.proyectmanager.task.application.usecases;

import java.util.List;

import com.api.proyectmanager.shared.domain.annotation.UseCase;
import com.api.proyectmanager.task.domain.Task;
import com.api.proyectmanager.task.domain.ports.TaskRepository;

@UseCase
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
