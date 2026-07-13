package com.api.proyectmanager.task.application.usecases;

import java.util.HashSet;
import java.util.List;

import org.springframework.stereotype.Service;

import com.api.proyectmanager.project.domain.Project;
import com.api.proyectmanager.project.domain.ports.ProjectRepository;
import com.api.proyectmanager.shared.domain.BusinessException;
import com.api.proyectmanager.task.application.dto.TaskCreateRequest;
import com.api.proyectmanager.task.domain.Task;
import com.api.proyectmanager.task.domain.TaskStatus;
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

    public void execute(TaskCreateRequest request, Integer currentUserId) {
        // Validar que el proyecto exista
        Project project = projectRepository.findById(request.projectId())
                .orElseThrow(() -> new BusinessException("Proyecto no encontrado"));
        // Validar que el usuario sea un admin o lider de proyecto
        if (!userRepository.isAdmin(currentUserId) && !project.getLeader().getId().equals(currentUserId)) {
            throw new BusinessException("No eres Admin o Líder del proyecto, no puedes crear tareas");
        }
        // Instanciar la tarea con los datos proporcionados
        Task task = new Task();
        task.setName(request.title());
        task.setDescription(request.description());
        task.setDueDate(request.dueDate());
        task.setState(TaskStatus.PENDIENTE); // Establecer el estado inicial de la tarea como pendiente
        task.setIsActive(true); // Establecer la tarea como activa por defecto
        // Validar y asignar el miembros asignados a la tarea
        if (request.membersIds() != null && !request.membersIds().isEmpty()) {
            // Tu excelente validación temprana se mantiene
            if (!projectRepository.areUsersMembersOfProject(request.projectId(), request.membersIds())) {
                throw new BusinessException("Uno o más usuarios asignados no son miembros del proyecto");
            }
            // Buscamos todos los usuarios por sus IDs
            List<User> users = userRepository.findAllByIds(request.membersIds());
            // Validamos que se hayan encontrado todos los IDs solicitados
            if (users.size() != request.membersIds().size()) {
                throw new BusinessException("Uno o más usuarios asignados no existen en el sistema");
            }
            task.setAssignedUsers(new HashSet<>(users));
        } else {
            // Si no hay miembros asignados, simplemente guardamos la tarea sin miembros
            task.setAssignedUsers(new HashSet<>()); // Inicializamos con un conjunto vacío
        }
        // Guardar la tarea en el repositorio
        taskRepository.save(task);
    }
}
