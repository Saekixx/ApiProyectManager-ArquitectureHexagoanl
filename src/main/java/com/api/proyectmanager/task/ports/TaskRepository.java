package com.api.proyectmanager.task.ports;

import java.util.List;

import com.api.proyectmanager.task.domain.Task;

public interface TaskRepository {
    // Puerto para guardar una tarea
    Task save(Task task);

    // Puerto para editar una tarea
    Task update(Task task);

    // Puerto para activar o desactivar una tarea según su ID y el estado activo o inactivo
    void setActive(Long id, boolean active);

    // Puerto para listar todas las tareas
    List<Task> findAll();

    // Puerto para listar todas las tareas por proyecto
    List<Task> findByProjectId(Long projectId);

    // Puerto para encontrar una tarea por su ID
    Task findById(Long id);
}
