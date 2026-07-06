package com.api.proyectmanager.task.application.usecases;

import java.util.List;

import org.springframework.stereotype.Service;

import com.api.proyectmanager.task.domain.Task;
import com.api.proyectmanager.task.domain.ports.TaskRepository;
import com.api.proyectmanager.user.domain.ports.UserRepository;

@Service
public class FindByUserTask {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public FindByUserTask(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public List<Task> execute(Integer userId) {
        // Validar que el usuario exista
        userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Obtener las tareas asignadas al usuario
        return taskRepository.findByAssignedUserId(userId);
    }
}
