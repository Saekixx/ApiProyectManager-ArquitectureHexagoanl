package com.api.proyectmanager.task.application.usecases;

import org.springframework.stereotype.Service;

import com.api.proyectmanager.project.domain.Project;
import com.api.proyectmanager.project.domain.ports.ProjectRepository;
import com.api.proyectmanager.shared.domain.BusinessException;
import com.api.proyectmanager.task.domain.Task;
import com.api.proyectmanager.task.domain.ports.TaskRepository;
import com.api.proyectmanager.user.domain.ports.UserRepository;

@Service("TaskUpdate")
public class Update {
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public Update(TaskRepository taskRepository, ProjectRepository projectRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    public void execute(Integer taskId, Integer projectId, Task updatedTask, Integer creatorId) {
        // Validar que el proyecto exista
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new BusinessException("Proyecto no encontrado"));
        // Validar que el usuario sea un admin o lider de proyecto
        if (!userRepository.isAdmin(creatorId) && !project.getLeader().getId().equals(creatorId)) {
            throw new BusinessException("No eres Admin o Líder del proyecto, no puedes editar tareas");
        }
        // Guardamos el proyecto en la tarea
        updatedTask.setProject(project);
        // Validar que la tarea exista
        Task existingTask = taskRepository.findById(taskId)
                .orElseThrow(() -> new BusinessException("Tarea no encontrada"));
        // Actualizamos los campos de la tarea existente con los valores de la tarea actualizada
        existingTask.setName(updatedTask.getName()); // Actualizamos el nombre
        existingTask.setDescription(updatedTask.getDescription()); // Actualizamos la descripción
        existingTask.setDueDate(updatedTask.getDueDate()); // Actualizamos la fecha de vencimiento
        // Guardamos la tarea actualizada en el repositorio
        taskRepository.save(existingTask);
    }
}
