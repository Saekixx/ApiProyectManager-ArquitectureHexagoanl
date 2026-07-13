package com.api.proyectmanager.project.domain.ports;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.api.proyectmanager.project.domain.Project;
import com.api.proyectmanager.user.domain.User;

public interface ProjectRepository {
    // Definición de métodos para la persistencia de proyectos (PORTS)
    // Guarda o actualiza un proyecto en la base de datos
    Project save(Project project);
    // Obtiene todos los proyectos
    List<Project> findAll();
    // Obtiene todo los proyectos activos del usuario
    List<Project> findAllActiveByUserId(Integer userId);
    // Obtiene un proyecto por su ID
    Optional<Project> findById(Integer id);
    // Obtiene todos los proyectos liderados por un líder específico
    List<Project> findByLeaderId(Integer leaderId);
    // Obtiene todos los proyectos en los que un miembro específico está involucrado
    List<Project> findByMemberId(Integer memberId);

    // Verifica si un proyecto existe por su ID
    Boolean existsById(Integer id);
    // Cambia el líder de un proyecto
    void changeLeader(Integer projectId, User newLeader); 
    // Verifica si un usuario es miembro de un proyecto específico
    Boolean isUserMemberOfProject(Integer projectId, Integer userId);
    // Verifica si una lista de usuarios son miembros de un proyecto específico
    Boolean areUsersMembersOfProject(Integer projectId, Set<Integer> userIds);
    // Verifica si un usuario es el líder de un proyecto específico
    Boolean isProjectLeader(Integer projectId, Integer userId);
}
