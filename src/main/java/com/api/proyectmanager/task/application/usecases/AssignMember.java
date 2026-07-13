package com.api.proyectmanager.task.application.usecases;

import java.util.Set;

import org.springframework.stereotype.Service;

import com.api.proyectmanager.project.domain.Project;
import com.api.proyectmanager.shared.domain.BusinessException;
import com.api.proyectmanager.task.domain.Task;
import com.api.proyectmanager.task.domain.ports.TaskRepository;
import com.api.proyectmanager.user.domain.User;
import com.api.proyectmanager.user.domain.ports.UserRepository;

@Service("TaskAssignMember")
public class AssignMember {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public AssignMember(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public void execute(Integer taskId, Integer memberId, Integer creatorId) {
        // Validar que la tarea exista
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new BusinessException("Tarea no encontrada"));
        // Obtenemos el proyecto asociado a la tarea
        Project project = task.getProject();
        // Validar que el usuario sea un admin o lider de proyecto
        Boolean isLeader = project.getLeader() != null && project.getLeader().getId().equals(creatorId);
        if (!userRepository.isAdmin(creatorId) && !isLeader) {
            throw new BusinessException("No tienes permisos para asignar miembros. Debes ser Admin o Líder del proyecto.");
        }
        // Obtenemos los miembros del proyecto asociado a la tarea
        Set<User> projectMembers = project.getProjectMembers().stream()
                .map(pm -> pm.getUser())
                .collect(java.util.stream.Collectors.toSet());
        // Buscamos al miembro directamente dentro de la lista ya cargada (usando el ID) y validamos que pertenezca al proyecto
        User member = projectMembers.stream()
                .filter(u -> u.getId().equals(memberId))
                .findFirst()
                .orElseThrow(() -> new BusinessException("El miembro no pertenece al proyecto asociado a la tarea"));
        // Obtener la lista de miembros asignados a la tarea
        Set<User> assignedMembers = task.getAssignedUsers();
        // Validamos si ya está asignado comparando IDs en memoria
        boolean alreadyAssigned = assignedMembers.stream()
            .anyMatch(u -> u.getId().equals(memberId));
        // Verificar si el miembro ya está asignado a la tarea
        if (alreadyAssigned) {
            throw new BusinessException("El miembro ya está asignado a la tarea");
        }
        // Asignar el miembro a la tarea
        assignedMembers.add(member);
        // Guardar la tarea actualizada en el repositorio
        taskRepository.save(task);
    }
    
}
