package com.api.proyectmanager.project.infrastructure.adapters.output.mapper.project;

import com.api.proyectmanager.project.domain.Project;
import com.api.proyectmanager.project.infrastructure.adapters.output.jpa.project.ProjectEntity;
import com.api.proyectmanager.user.infrastructure.adapters.output.mapper.user.UserMapper;

// Clase para mapear los proyectos de la base de datos a objetos de dominio y viceversa
public class ProjectMapper {
    // Metodo para mapear de ProjectEntity a Project
    public static Project toDomain(ProjectEntity entity) {
        if (entity == null) {
            return null;
        }
        return new Project(
            entity.getId(), 
            entity.getName(), 
            entity.getDescription(),
            entity.getIsActive(),
            UserMapper.toDomain(entity.getLeader()), // Mapeo del líder del proyecto
            entity.getCreatedAt(), 
            entity.getUpdatedAt()
        );
    }

    // Metodo para mapear de Project a ProjectEntity
    public static ProjectEntity toEntity(Project project) {
        
        if (project == null) {
            return null;
        }

        ProjectEntity entity = new ProjectEntity();
        entity.setId(project.getId());
        entity.setName(project.getName());
        entity.setDescription(project.getDescription());
        entity.setIsActive(project.isActive());
        entity.setLeader(UserMapper.toEntity(project.getLeader())); // Mapeo del líder del proyecto
        entity.setCreatedAt(project.getCreatedAt());
        entity.setUpdatedAt(project.getUpdatedAt());
        return entity;
    }
}
