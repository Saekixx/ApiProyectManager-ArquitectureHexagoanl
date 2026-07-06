package com.api.proyectmanager.task.application.usecases;

import java.util.List;

import org.springframework.stereotype.Service;

import com.api.proyectmanager.task.domain.Task;
import com.api.proyectmanager.task.domain.ports.TaskRepository;
import com.api.proyectmanager.user.domain.ports.UserRepository;

@Service("TaskFindAllActiveByUserId")
public class FindAllActiveByUserId {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public FindAllActiveByUserId(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public List<Task> execute(Integer userId) {
        // Verificamos si el usuario existe en la base de datos
        userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        // Retornamos la lista de tareas activas asociadas al usuario
        return taskRepository.findAllActiveByUserId(userId);
    }
}
