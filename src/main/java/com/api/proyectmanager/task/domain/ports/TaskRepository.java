package com.api.proyectmanager.task.domain.ports;

import java.util.List;
import java.util.Optional;

import com.api.proyectmanager.task.domain.Task;

public interface TaskRepository {
    // Puerto para guardar o actualizar una tarea
    Task save(Task task);
    // Puerto para listar todas las tareas
    List<Task> findAll();
    // Puerto para listar todas las tareas activas de un usuario específico
    List<Task> findAllActiveByUserId(Integer userId);
    // Puerto para obtener una tarea por su ID
    Optional<Task> findById(Integer id);
    // Puerto para listar todas las tareas asociadas a un proyecto específico
    List<Task> findByProjectId(Integer projectId);
    // Puerto para listar todas las tareas asignadas a un usuario específico
    List<Task> findByAssignedUserId(Integer userId);
    // Puerto para desactivar una tarea
    void deactivateTask(Integer taskId);
}
