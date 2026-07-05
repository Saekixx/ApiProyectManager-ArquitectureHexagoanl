package com.api.proyectmanager.task.application;

import java.util.Set;

import org.springframework.stereotype.Service;

import com.api.proyectmanager.project.domain.Project;
import com.api.proyectmanager.project.domain.ports.ProjectRepository;
import com.api.proyectmanager.shared.domain.BusinessException;
import com.api.proyectmanager.task.domain.Task;
import com.api.proyectmanager.task.domain.ports.TaskRepository;
import com.api.proyectmanager.user.domain.User;
import com.api.proyectmanager.user.domain.ports.UserRepository;

@Service("TaskRemoveMember")
public class RemoveMember {
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public RemoveMember(TaskRepository taskRepository, ProjectRepository projectRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    public void execute(Integer taskId, Integer memberId, Integer editorId) {
        // Validar que la tarea exista
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new BusinessException("Tarea no encontrada"));
        // Extraer el ID del proyecto asociado a la tarea
        Integer projectId = task.getProject().getId();
        // Validar que el proyecto exista 
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new BusinessException("Proyecto no encontrado"));
        // Validar que el usuario sea un admin o lider de proyecto
        Boolean isLeader = project.getLeader() != null && project.getLeader().getId().equals(editorId);
        if (!userRepository.isAdmin(editorId) && !isLeader) {
            throw new BusinessException("No tienes permisos para asignar miembros. Debes ser Admin o Líder del proyecto.");
        }
        // Validar que el miembro exista
        User member = userRepository.findById(memberId)
                .orElseThrow(() -> new BusinessException("Miembro no encontrado"));
        // Obtener la lista de miembros asignados a la tarea
        Set<User> assignedMembers = task.getAssignedUsers();
        // Verificar si el miembro está asignado a la tarea
        if (!assignedMembers.contains(member)) {
            throw new BusinessException("El miembro no está asignado a la tarea");
        }
        // Remover el miembro de la tarea
        assignedMembers.remove(member);
        // Actualizar la lista de miembros asignados en la tarea
        task.setAssignedUsers(assignedMembers);
        // Guardar los cambios en el repositorio
        taskRepository.save(task);
    }
}
