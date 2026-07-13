package com.api.proyectmanager.task.application.usecases;

import com.api.proyectmanager.shared.domain.BusinessException;
import com.api.proyectmanager.shared.domain.annotation.UseCase;
import com.api.proyectmanager.task.domain.Task;
import com.api.proyectmanager.task.domain.ports.TaskRepository;
import com.api.proyectmanager.user.domain.ports.UserRepository;

@UseCase
public class CompleteTask {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public CompleteTask(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public void execute(Integer taskId, Integer userId) {
        // Validar que la tarea exista
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new BusinessException("Tarea no encontrada"));
        // Validar que el usuario sea un admin, líder del proyecto o asignado a la tarea
        Boolean isAdmin = userRepository.isAdmin(userId);
        Boolean isLeader = task.getProject().getLeader() != null && task.getProject().getLeader().getId().equals(userId);
        Boolean isAssigned = task.getAssignedUsers().stream().anyMatch(u -> u.getId().equals(userId));
        if (!isAdmin && !isLeader && !isAssigned) {
            throw new BusinessException("No tienes permisos para completar esta tarea. Debes ser Admin, Líder del proyecto o estar asignado a ella.");
        }
        // Cambiar el estado de la tarea a "Completada"
        task.getState().completar(task);
        // Guardar los cambios en el repositorio
        taskRepository.save(task);
    }
}
