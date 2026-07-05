package com.api.proyectmanager.task.infrastructure.adapters.output.mapper;

import com.api.proyectmanager.project.infrastructure.adapters.output.mapper.project.ProjectMapper;
import com.api.proyectmanager.task.domain.Task;
import com.api.proyectmanager.task.infrastructure.adapters.output.jpa.TaskEntity;
import com.api.proyectmanager.user.infrastructure.adapters.output.mapper.user.UserMapper;

// Clase para mapear entre Task y TaskEntity
public class TaskMapper {
    // Metodo para mapear de TaskEntity a Task
    public static Task toDomain(TaskEntity entity) {
        if (entity == null) {
            return null;
        }

        return new Task(
            entity.getId(),
            entity.getName(),
            entity.getDescription(),
            entity.getState(),
            entity.getDueDate(),
            entity.getIsActive(),
            ProjectMapper.toDomain(entity.getProject()),
            UserMapper.toDomainSet(entity.getAssignedUsers()),
            entity.getCreatedAt(),
            entity.getUpdatedAt()
        );
    }

    // Metodo para mapear de Task a TaskEntity
    public static TaskEntity toEntity(Task task) {
        if (task == null) {
            return null;
        }

        TaskEntity entity = new TaskEntity();
        entity.setId(task.getId());
        entity.setName(task.getName());
        entity.setDescription(task.getDescription());
        entity.setState(task.getState());
        entity.setDueDate(task.getDueDate());
        entity.setIsActive(task.getIsActive());
        entity.setProject(ProjectMapper.toEntity(task.getProject()));
        entity.setAssignedUsers(UserMapper.toEntitySet(task.getAssignedUsers()));
        entity.setCreatedAt(task.getCreatedAt());
        entity.setUpdatedAt(task.getUpdatedAt());

        return entity;
    }
}
