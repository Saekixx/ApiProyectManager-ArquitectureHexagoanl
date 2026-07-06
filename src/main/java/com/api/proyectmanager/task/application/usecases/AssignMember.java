package com.api.proyectmanager.task.application.usecases;


import java.util.Set;

import org.springframework.stereotype.Service;

import com.api.proyectmanager.project.domain.Project;
import com.api.proyectmanager.project.domain.ports.ProjectRepository;
import com.api.proyectmanager.shared.domain.BusinessException;
import com.api.proyectmanager.task.domain.Task;
import com.api.proyectmanager.task.domain.ports.TaskRepository;
import com.api.proyectmanager.user.domain.User;
import com.api.proyectmanager.user.domain.ports.UserRepository;

@Service("TaskAssignMember")
public class AssignMember {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    public AssignMember(TaskRepository taskRepository, UserRepository userRepository, ProjectRepository projectRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
    }

    public void execute(Integer taskId, Integer memberId, Integer creatorId) {
        // Validar que la tarea exista
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new BusinessException("Tarea no encontrada"));
        // Extraer el ID del proyecto asociado a la tarea
        Integer projectId = task.getProject().getId();
        // Validar que el proyecto exista 
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new BusinessException("Proyecto no encontrado"));
        // Validar que el usuario sea un admin o lider de proyecto
        Boolean isLeader = project.getLeader() != null && project.getLeader().getId().equals(creatorId);
        if (!userRepository.isAdmin(creatorId) && !isLeader) {
            throw new BusinessException("No tienes permisos para asignar miembros. Debes ser Admin o Líder del proyecto.");
        }
        // Validar que el miembro exista
        User member = userRepository.findById(memberId)
                .orElseThrow(() -> new BusinessException("Miembro no encontrado"));
        // Reutilizamos el método individual que creamos en tu adaptador de Proyectos
        if (!projectRepository.isUserMemberOfProject(projectId, memberId)) {
            throw new BusinessException("El usuario no puede ser asignado a la tarea porque no es miembro de este proyecto");
        }
        // Obtener la lista de miembros asignados a la tarea
        Set<User> assignedMembers = task.getAssignedUsers();
        // Verificar si el miembro ya está asignado a la tarea
        if (assignedMembers.contains(member)) {
            throw new BusinessException("El miembro ya está asignado a la tarea");
        }
        // Asignar el miembro a la tarea
        assignedMembers.add(member);
        // Guardar la tarea actualizada en el repositorio
        taskRepository.save(task);
    }
    
}
