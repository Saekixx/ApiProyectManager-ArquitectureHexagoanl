package com.api.proyectmanager.project.infrastructure.adapters.output.mapper.project;

import java.util.Collections;
import java.util.Set;

import com.api.proyectmanager.project.domain.Project;
import com.api.proyectmanager.project.infrastructure.adapters.output.jpa.project.ProjectEntity;
import com.api.proyectmanager.project.infrastructure.adapters.output.jpa.projectMiembro.ProjectMiembroEntity;
import com.api.proyectmanager.project.infrastructure.adapters.output.mapper.projectMiembro.ProjectMiembroMapper;
import com.api.proyectmanager.user.infrastructure.adapters.output.mapper.user.UserMapper;

// Clase para mapear los proyectos de la base de datos a objetos de dominio y viceversa
public class ProjectMapper {
    // Metodo para mapear de ProjectEntity a Project (sin miembros) para consultas rápidas y evitar ciclos de referencia
    public static Project toDomainShallow(ProjectEntity entity) {
        if (entity == null) {
            return null;
        }
        return new Project(
            entity.getId(), 
            entity.getName(), 
            entity.getDescription(),
            entity.getIsActive(),
            UserMapper.toDomain(entity.getLeader()),
            Collections.emptySet(),
            entity.getCreatedAt(), 
            entity.getUpdatedAt()
        );
    }


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
            ProjectMiembroMapper.toDomainList(entity.getProjectMembers()), // Mapeo de los miembros del proyecto
            entity.getCreatedAt(), 
            entity.getUpdatedAt()
        );
    }

    // Metodo para mapear de Project a ProjectEntity
    public static ProjectEntity toEntity(Project project) {
        if (project == null) {
            return null;
        }

        // Creamos una nueva instancia de ProjectEntity y establecemos sus propiedades a partir del objeto Project
        ProjectEntity entity = new ProjectEntity();
        entity.setId(project.getId());
        entity.setName(project.getName());
        entity.setDescription(project.getDescription());
        entity.setIsActive(project.isActive());
        entity.setLeader(UserMapper.toEntity(project.getLeader())); 

        // Mapeamos los miembros del proyecto a entidades y establecemos la relación bidireccional
        Set<ProjectMiembroEntity> membersEntities = ProjectMiembroMapper.toEntityList(project.getProjectMembers());
        
        // Establecemos la relación bidireccional entre ProjectEntity y ProjectMiembroEntity
        if (membersEntities != null) {
            membersEntities.forEach(member -> member.setProject(entity));
        }
        entity.setProjectMembers(membersEntities);
        entity.setCreatedAt(project.getCreatedAt());
        entity.setUpdatedAt(project.getUpdatedAt());
        
        // Devolvemos la entidad mapeada
        return entity;
    }
}
