package com.api.proyectmanager.project.infrastructure.adapters.output;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.api.proyectmanager.project.domain.Project;
import com.api.proyectmanager.project.domain.ports.ProjectRepository;
import com.api.proyectmanager.project.infrastructure.adapters.output.jpa.project.ProjectEntity;
import com.api.proyectmanager.project.infrastructure.adapters.output.jpa.project.SpringDataProjectRepository;
import com.api.proyectmanager.project.infrastructure.adapters.output.mapper.project.ProjectMapper;
import com.api.proyectmanager.user.domain.User;
import com.api.proyectmanager.user.infrastructure.adapters.output.jpa.user.SpringDataUserRepository;
import com.api.proyectmanager.user.infrastructure.adapters.output.jpa.user.UserEntity;

import org.springframework.transaction.annotation.Transactional;

//Adaptador de persistencia para los proyectos
@Component
public class ProjectPersistenceAdapter implements ProjectRepository {
    private final SpringDataProjectRepository springDataProjectRepository;
    private final SpringDataUserRepository springDataUserRepository;

    public ProjectPersistenceAdapter(SpringDataProjectRepository springDataProjectRepository, SpringDataUserRepository springDataUserRepository) {
        this.springDataProjectRepository = springDataProjectRepository;
        this.springDataUserRepository = springDataUserRepository;
    }

    /* Este adaptador implementa los métodos del puerto ProjectRepository para 
       interactuar con la base de datos a través del repositorio JPA */

    @Override
    @Transactional
    public Project save(Project project) {
        // Convertimos el objeto Project a ProjectEntity usando el mapper
        ProjectEntity entity = ProjectMapper.toEntity(project);
        // Guardamos la entidad en la base de datos usando el repositorio JPA
        ProjectEntity savedEntity = springDataProjectRepository.save(entity);
        // Convertimos la entidad guardada de nuevo a Project y la retornamos
        return ProjectMapper.toDomain(savedEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Project> findAll() {
        // Obtenemos todos los proyectos desde la base de datos usando el repositorio JPA
        List<ProjectEntity> entities = springDataProjectRepository.findAll();
        // Convertimos la lista de ProjectEntity a una lista de Project y la retornamos
        return entities.stream().map(ProjectMapper::toDomain).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Project> findById(Integer projectId) {
        // Buscamos el proyecto por ID usando el repositorio JPA
        Optional<ProjectEntity> entityOptional = springDataProjectRepository.findById(projectId);
        // Convertimos el objeto ProjectEntity a Project si está presente
        return entityOptional.map(ProjectMapper::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Project> findByLeaderId(Integer leaderId) {
        // Buscamos los proyectos por ID de líder usando el repositorio JPA
        List<ProjectEntity> entities = springDataProjectRepository.findByLeaderId(leaderId);
        // Convertimos la lista de ProjectEntity a una lista de Project y la retornamos
        return entities.stream().map(ProjectMapper::toDomain).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Project> findByMemberId(Integer memberId) {
        // Buscamos los proyectos por ID de miembro usando el repositorio JPA
        List<ProjectEntity> entities = springDataProjectRepository.findByMemberId(memberId);
        // Convertimos la lista de ProjectEntity a una lista de Project y la retornamos
        return entities.stream().map(ProjectMapper::toDomain).toList();
    }

    @Override
    @Transactional
    public void changeLeader(Integer projectId, User newLeader) {
        // Buscamos el proyecto por ID usando el repositorio JPA
        Optional<ProjectEntity> entityOptional = springDataProjectRepository.findById(projectId);
        if (entityOptional.isPresent()) {
            ProjectEntity entity = entityOptional.get();
            // Convertimos el objeto User a UserEntity usando el repositorio JPA
            UserEntity newLeaderEntity = springDataUserRepository.findById(newLeader.getId())
                    .orElseThrow(() -> new IllegalArgumentException("El usuario con ID " + newLeader.getId() + " no existe."));
            // Cambiamos el líder del proyecto
            entity.setLeader(newLeaderEntity);
            // Guardamos el proyecto actualizado en la base de datos
            springDataProjectRepository.save(entity);
        } else {
            throw new RuntimeException("El proyecto con ID " + projectId + " no existe.");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean isUserMemberOfProject(Integer projectId, Integer userId) {
        // Verificamos si un usuario es miembro de un proyecto específico usando el repositorio JPA
        return springDataProjectRepository.existsByIdAndMembers_Id(projectId, userId);
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean areUsersMembersOfProject(Integer projectId, Set<Integer> userIds) {
        // Verificamos si todos los usuarios de la lista son miembros de un proyecto específico
        for (Integer userId : userIds) {
            if (!isUserMemberOfProject(projectId, userId)) {
                return false; // Si algún usuario no es miembro, retornamos false
            }
        }
        return true; // Todos los usuarios son miembros del proyecto
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean isProjectLeader(Integer projectId, Integer userId) {
        // Verificamos si un usuario es el líder de un proyecto específico usando el repositorio JPA
        return springDataProjectRepository.existsByIdAndLeader_Id(projectId, userId);
    }
}
