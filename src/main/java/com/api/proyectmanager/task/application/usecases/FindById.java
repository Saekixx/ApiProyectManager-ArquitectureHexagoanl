package com.api.proyectmanager.task.application.usecases;

import org.springframework.stereotype.Service;

import com.api.proyectmanager.shared.domain.BusinessException;
import com.api.proyectmanager.task.domain.Task;
import com.api.proyectmanager.task.domain.ports.TaskRepository;

@Service("TaskFindById")
public class FindById {
    private final TaskRepository taskRepository;

    public FindById(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task execute(Integer taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new BusinessException("Tarea no encontrada"));
    }
}
