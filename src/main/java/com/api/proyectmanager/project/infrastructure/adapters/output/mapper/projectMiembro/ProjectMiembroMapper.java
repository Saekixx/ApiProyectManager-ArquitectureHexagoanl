package com.api.proyectmanager.project.infrastructure.adapters.output.mapper.projectMiembro;

import com.api.proyectmanager.project.domain.ProjectMiembro;
import com.api.proyectmanager.project.infrastructure.adapters.output.jpa.projectMiembro.ProjectMiembroEntity;
import com.api.proyectmanager.project.infrastructure.adapters.output.mapper.project.ProjectMapper;
import com.api.proyectmanager.user.infrastructure.adapters.output.mapper.user.UserMapper;

// Clase para mapear los miembros del proyecto de la base de datos a objetos de dominio y viceversa
public class ProjectMiembroMapper {
    // Metodo para mapear de ProjectMiembroEntity a ProjectMiembro
    public static ProjectMiembro toDomain(ProjectMiembroEntity entity) {
        if (entity == null) {
            return null;
        }
        return new ProjectMiembro(
            entity.getId(),
            ProjectMapper.toDomain(entity.getProject()), // Mapeo del proyecto
            UserMapper.toDomain(entity.getUser()), // Mapeo del usuario
            entity.getIsActive(),
            entity.getJoinedAt(),
            entity.getExitedAt()
        );
    }

    // Metodo para mapear de ProjectMiembro a ProjectMiembroEntity
    public static ProjectMiembroEntity toEntity(ProjectMiembro projectMiembro) {
        if (projectMiembro == null) {
            return null;
        }
        ProjectMiembroEntity entity = new ProjectMiembroEntity();
        entity.setId(projectMiembro.getId());
        entity.setProject(ProjectMapper.toEntity(projectMiembro.getProject())); // Mapeo del proyecto
        entity.setUser(UserMapper.toEntity(projectMiembro.getUser())); // Mapeo del usuario
        entity.setIsActive(projectMiembro.getIsActive());
        entity.setJoinedAt(projectMiembro.getJoinedAt());
        entity.setExitedAt(projectMiembro.getExitedAt());
        return entity;
    }
}
