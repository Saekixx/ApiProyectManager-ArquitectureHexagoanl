package com.api.proyectmanager.project.infrastructure.adapters.output.mapper.projectMiembro;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.api.proyectmanager.project.domain.ProjectMiembro;
import com.api.proyectmanager.project.infrastructure.adapters.output.jpa.projectMiembro.ProjectMiembroEntity;
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
            null, // El proyecto se puede mapear si es necesario, pero aquí se deja como null para evitar ciclos de referencia
            UserMapper.toDomain(entity.getUser()), // Mapeo del usuario
            entity.getIsActive(),
            entity.getJoinedAt(),
            entity.getExitedAt()
        );
    }

    // Metodo para mapear de una lista de ProjectMiembroEntity a una lista de ProjectMiembro
    public static List<ProjectMiembro> toDomainList(List<ProjectMiembroEntity> entities) {
        if (entities == null) return Collections.emptyList();
        return entities.stream()
                       .map(ProjectMiembroMapper::toDomain)
                       .collect(Collectors.toList());
    }

    // Metodo para mapear de ProjectMiembro a ProjectMiembroEntity
    public static ProjectMiembroEntity toEntity(ProjectMiembro projectMiembro) {
        if (projectMiembro == null) return null;
        
        ProjectMiembroEntity entity = new ProjectMiembroEntity();
        entity.setId(projectMiembro.getId());
        // El proyecto se asociará arriba en el flujo para evitar ciclos de mapeo
        entity.setUser(UserMapper.toEntity(projectMiembro.getUser())); 
        entity.setIsActive(projectMiembro.getIsActive());
        entity.setJoinedAt(projectMiembro.getJoinedAt());
        entity.setExitedAt(projectMiembro.getExitedAt());
        return entity;
    }

    // Metodo para mapear de una lista de ProjectMiembro a una lista de ProjectMiembroEntity
    public static List<ProjectMiembroEntity> toEntityList(List<ProjectMiembro> domainList) {
        if (domainList == null) return Collections.emptyList();
        return domainList.stream()
                         .map(ProjectMiembroMapper::toEntity)
                         .collect(Collectors.toList());
    }
}
