package com.api.proyectmanager.project.infrastructure.adapters.output.mapper.projectMiembro;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.api.proyectmanager.project.domain.Project;
import com.api.proyectmanager.project.domain.ProjectMiembro;
import com.api.proyectmanager.project.infrastructure.adapters.output.jpa.project.ProjectEntity;
import com.api.proyectmanager.project.infrastructure.adapters.output.jpa.projectMiembro.ProjectMiembroEntity;
import com.api.proyectmanager.user.infrastructure.adapters.output.mapper.user.UserMapper;

// Clase para mapear los miembros del proyecto de la base de datos a objetos de dominio y viceversa
public class ProjectMiembroMapper {
    // Metodo para mapear de ProjectMiembroEntity a ProjectMiembro
    public static ProjectMiembro toDomain(ProjectMiembroEntity entity) {
        if (entity == null) {
            return null;
        }

        // Obtenemos el proyecto asociado al miembro del proyecto, si existe
        Project projectDomain = null;
        if (entity.getProject() != null) {
            // Creamos un objeto Project de dominio con solo el ID para evitar duplicados y ciclos de referencia
            projectDomain = new Project();
            // Asignamos el ID del proyecto desde la entidad a la instancia de dominio
            projectDomain.setId(entity.getProject().getId());
        }

        return new ProjectMiembro(
            entity.getId(),
            projectDomain, // Asignamos el proyecto mapeado (para evitar duplicados y ciclos de referencia)
            UserMapper.toDomain(entity.getUser()), // Mapeo del usuario
            entity.getIsActive(),
            entity.getJoinedAt(),
            entity.getExitedAt()
        );
    }

    // Metodo para mapear de una lista de ProjectMiembroEntity a una lista de ProjectMiembro
    public static Set<ProjectMiembro> toDomainList(Set<ProjectMiembroEntity> entities) {
        if (entities == null) return Collections.emptySet();
        // Se crea una copia en memoria para evitar conflictos con la inicialización perezosa (Lazy) de Hibernate
        Set<ProjectMiembroEntity> safeCopy = new HashSet<>(entities);
        return safeCopy.stream()
                       .map(ProjectMiembroMapper::toDomain)
                       .collect(Collectors.toSet());
    }

    // Metodo para mapear de ProjectMiembro a ProjectMiembroEntity
    public static ProjectMiembroEntity toEntity(ProjectMiembro projectMiembro) {
        if (projectMiembro == null) return null;
        
        ProjectMiembroEntity entity = new ProjectMiembroEntity();
        entity.setId(projectMiembro.getId());

        // Asignamos la referencia al Proyecto usando solo el ID
        if (projectMiembro.getProject() != null && projectMiembro.getProject().getId() != null) {
            ProjectEntity projectEntity = new ProjectEntity();
            projectEntity.setId(projectMiembro.getProject().getId());
            entity.setProject(projectEntity);
        }

        // Asignamos la referencia al Usuario usando el mapeador de usuarios
        entity.setUser(UserMapper.toEntity(projectMiembro.getUser()));
        
        // Asignamos los demás campos
        entity.setIsActive(projectMiembro.getIsActive());
        entity.setJoinedAt(projectMiembro.getJoinedAt());
        entity.setExitedAt(projectMiembro.getExitedAt());
        return entity;
    }

    // Metodo para mapear de una lista de ProjectMiembro a una lista de ProjectMiembroEntity
    public static Set<ProjectMiembroEntity> toEntityList(Set<ProjectMiembro> domainList) {
        if (domainList == null) return Collections.emptySet();
        // Se crea una copia en memoria para evitar conflictos con la inicialización perezosa (Lazy) de Hibernate
        Set<ProjectMiembro> safeCopy = new HashSet<>(domainList);
        return safeCopy.stream()
                       .map(ProjectMiembroMapper::toEntity)
                       .collect(Collectors.toSet());
    }
}