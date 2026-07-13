package com.api.proyectmanager.task.application.usecases;

import org.springframework.stereotype.Service;

import com.api.proyectmanager.project.domain.Project;
import com.api.proyectmanager.shared.domain.BusinessException;
import com.api.proyectmanager.task.application.dto.TaskUpdateRequest;
import com.api.proyectmanager.task.domain.Task;
import com.api.proyectmanager.task.domain.ports.TaskRepository;
import com.api.proyectmanager.user.domain.ports.UserRepository;

@Service("TaskUpdate")
public class Update {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public Update(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public void execute(Integer taskId, TaskUpdateRequest request) {
        // Validar que la tarea exista
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new BusinessException("Tarea no encontrada con el ID: " + taskId));
        // Obtenemos el proyecto asociado a la tarea
        Project project = task.getProject();
        // Validar que el usuario sea un admin o lider de proyecto
        if (!userRepository.isAdmin(request.creatorId()) && !project.getLeader().getId().equals(request.creatorId())) {
            throw new BusinessException("No eres Admin o Líder del proyecto, no puedes actualizar tareas");
        }
        task.actualizarDatosGenerales(
            request.title(),       // Actualizamos el nombre de la tarea
            request.description(), // Actualizamos la descripción de la tarea
            request.dueDate()      // Actualizamos la fecha de vencimiento de la tarea
        );
        // Guardamos la tarea actualizada en el repositorio
        taskRepository.save(task);
    }
}
