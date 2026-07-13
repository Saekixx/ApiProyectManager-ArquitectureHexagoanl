package com.api.proyectmanager.task.application.usecases;

import java.util.Set;

import com.api.proyectmanager.project.domain.Project;
import com.api.proyectmanager.shared.domain.BusinessException;
import com.api.proyectmanager.shared.domain.annotation.UseCase;
import com.api.proyectmanager.task.domain.Task;
import com.api.proyectmanager.task.domain.ports.TaskRepository;
import com.api.proyectmanager.user.domain.User;
import com.api.proyectmanager.user.domain.ports.UserRepository;

@UseCase
public class RemoveMember {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public RemoveMember(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public void execute(Integer taskId, Integer memberId, Integer currentUserId) {
        // Validar que la tarea exista
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new BusinessException("Tarea no encontrada"));
        // Obtener el proyecto asociado a la tarea
        Project project = task.getProject();
        // Validar que el usuario sea un admin o lider de proyecto
        if (!userRepository.isAdmin(currentUserId) && !project.getLeader().getId().equals(currentUserId)) {
            throw new BusinessException("No eres Admin o Líder del proyecto, no puedes remover miembros de la tarea");
        }
        // Obtener la lista de miembros asignados a la tarea
        Set<User> assignedMembers = task.getAssignedUsers();
        // Buscamos al miembro directamente dentro de la lista ya cargada (usando el ID)
        User memberToRemove = assignedMembers.stream()
            .filter(user -> user.getId().equals(memberId))
            .findFirst()
            .orElseThrow(() -> new BusinessException("El miembro con ID " + memberId + " no está asignado a esta tarea"));
        // Remover el usuario encontrado y guardar
        assignedMembers.remove(memberToRemove);
        // Guardar la tarea actualizada en el repositorio
        taskRepository.save(task);
    }
}
