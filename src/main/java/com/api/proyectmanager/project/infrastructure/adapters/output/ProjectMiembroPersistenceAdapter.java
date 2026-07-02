package com.api.proyectmanager.project.infrastructure.adapters.output;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.api.proyectmanager.project.domain.ProjectMiembro;
import com.api.proyectmanager.project.domain.ports.ProjectMemberRepository;
import com.api.proyectmanager.project.infrastructure.adapters.output.jpa.projectMiembro.ProjectMiembroEntity;
import com.api.proyectmanager.project.infrastructure.adapters.output.jpa.projectMiembro.SpringDataProjectMiembroRepository;
import com.api.proyectmanager.project.infrastructure.adapters.output.mapper.projectMiembro.ProjectMiembroMapper;

// Adaptador de persistencia para los miembros del proyecto
@Component
public class ProjectMiembroPersistenceAdapter implements ProjectMemberRepository {
    private final SpringDataProjectMiembroRepository projectMiembroJpaRepository; // Repositorio JPA para miembros del proyecto

    public ProjectMiembroPersistenceAdapter(SpringDataProjectMiembroRepository projectMiembroJpaRepository) {
        this.projectMiembroJpaRepository = projectMiembroJpaRepository;
    }

    @Override
    @Transactional
    public ProjectMiembro save(ProjectMiembro projectMiembroEntity) {
        // Convertimos el objeto ProjectMiembro a ProjectMiembroEntity usando el mapper
        ProjectMiembroEntity entity = ProjectMiembroMapper.toEntity(projectMiembroEntity);
        // Guardamos la entidad en la base de datos usando el repositorio JPA
        ProjectMiembroEntity savedEntity = projectMiembroJpaRepository.save(entity);
        // Convertimos la entidad guardada de nuevo a ProjectMiembro y la retornamos
        return ProjectMiembroMapper.toDomain(savedEntity);
    }

    @Override
    @Transactional
    public Optional<ProjectMiembro> findByProjectIdAndUserId(Integer projectId, Integer userId) {
        // Buscamos la entidad en la base de datos usando el repositorio JPA
        return projectMiembroJpaRepository.findByProjectIdAndUserId(projectId, userId)
                .map(ProjectMiembroMapper::toDomain); // Convertimos la entidad encontrada a ProjectMiembro si existe
    }

    @Override
    @Transactional
    public List<ProjectMiembro> findActiveMembersByProjectId(Integer projectId) {
        // Buscamos todos los miembros activos de un proyecto usando el repositorio JPA
        return projectMiembroJpaRepository.findAll()
                .stream()
                .filter(member -> member.getProject().getId().equals(projectId) && member.getIsActive())
                .map(ProjectMiembroMapper::toDomain)
                .toList();
    }

    @Override
    @Transactional
    public List<ProjectMiembro> findAllMembersByProjectId(Integer projectId) {
        // Buscamos todos los miembros (activos e inactivos) de un proyecto usando el repositorio JPA
        return projectMiembroJpaRepository.findAll()
                .stream()
                .filter(member -> member.getProject().getId().equals(projectId))
                .map(ProjectMiembroMapper::toDomain)
                .toList();
    }
}
