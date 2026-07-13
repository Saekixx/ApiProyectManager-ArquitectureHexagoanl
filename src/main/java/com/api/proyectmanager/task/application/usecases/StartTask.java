package com.api.proyectmanager.task.application.usecases;

import com.api.proyectmanager.shared.domain.BusinessException;
import com.api.proyectmanager.shared.domain.annotation.UseCase;
import com.api.proyectmanager.task.domain.Task;
import com.api.proyectmanager.task.domain.ports.TaskRepository;

@UseCase
public class StartTask {
    private final TaskRepository taskRepository;  

    public StartTask(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public void execute(Integer taskId, Integer userId) {
        // Validar que la tarea exista
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new BusinessException("Tarea no encontrada"));
        // Validar que el usuario este asignado a la tarea
        Boolean isAssigned = task.getAssignedUsers().stream().anyMatch(u -> u.getId().equals(userId));
        if (!isAssigned) {
            throw new BusinessException("No tienes permisos para iniciar esta tarea. Debes estar asignado a ella.");
        }
        // Cambiar el estado de la tarea a "En Progreso"
        task.getState().iniciarProgreso(task);
        // Guardar los cambios en el repositorio
        taskRepository.save(task);
    }
}
