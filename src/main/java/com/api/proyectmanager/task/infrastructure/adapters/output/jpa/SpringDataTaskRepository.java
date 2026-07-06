package com.api.proyectmanager.task.infrastructure.adapters.output.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataTaskRepository extends JpaRepository<TaskEntity, Integer> {
    // Método para buscar tareas por el ID del proyecto
    List<TaskEntity> findByProjectId(Integer projectId);
    // Método para buscar tareas por el ID del usuario asignado
    List<TaskEntity> findByAssignedUsers_Id(Integer userId);
}
