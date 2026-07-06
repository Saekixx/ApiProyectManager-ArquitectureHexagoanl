package com.api.proyectmanager.task.application.usecases;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.api.proyectmanager.project.domain.Project;
import com.api.proyectmanager.project.domain.ports.ProjectRepository;
import com.api.proyectmanager.shared.domain.BusinessException;
import com.api.proyectmanager.task.domain.Task;
import com.api.proyectmanager.task.domain.ports.TaskRepository;
import com.api.proyectmanager.user.domain.User;
import com.api.proyectmanager.user.domain.ports.UserRepository;

@Service("TaskSave")
public class Save {
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public Save(TaskRepository taskRepository, ProjectRepository projectRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    public void execute(Task task, Integer projectId, Set<Integer> userIds, Integer creatorId) {
        // Validar que el proyecto exista
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new BusinessException("Proyecto no encontrado"));
        // Validar que el usuario sea un admin o lider de proyecto
        if (!userRepository.isAdmin(creatorId) && !project.getLeader().getId().equals(creatorId)) {
            throw new BusinessException("No eres Admin o Líder del proyecto, no puedes crear tareas");
        }
        // Guardamos el proyecto en la tarea
        task.setProject(project);
        // Validar que todos los usuarios asignados sean miembros del proyecto
        if (userIds != null && !userIds.isEmpty()) {
            // Validamos que los usuarios pertenezcan al proyecto antes de hacer consultas pesadas
            if (!projectRepository.areUsersMembersOfProject(projectId, userIds)) {
                throw new BusinessException("Uno o más usuarios asignados no son miembros del proyecto");
            }
            // Si todos los usuarios son miembros del proyecto, los obtenemos y los asignamos a la tarea
            Set<User> assignedUsers = new HashSet<>();
            for (Integer userId : userIds) {
                User user = userRepository.findById(userId)
                        .orElseThrow(() -> new BusinessException("Usuario asignado no encontrado con el ID: " + userId));
                assignedUsers.add(user);
            }
            // Asignamos los usuarios a la tarea
            task.setAssignedUsers(assignedUsers);
        }
        // Guardamos la tarea en el repositorio
        taskRepository.save(task);
    }
}
