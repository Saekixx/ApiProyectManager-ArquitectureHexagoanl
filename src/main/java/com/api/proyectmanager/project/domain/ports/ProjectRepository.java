package com.api.proyectmanager.project.domain.ports;

import java.util.List;
import java.util.Optional;

import com.api.proyectmanager.project.domain.Project;
import com.api.proyectmanager.user.domain.User;

public interface ProjectRepository {
    // Definición de métodos para la persistencia de proyectos (PORTS)
    // Guarda o actualiza un proyecto en la base de datos
    Project save(Project project);
    // Obtiene todos los proyectos
    List<Project> findAll();
    // Obtiene un proyecto por su ID
    Optional<Project> findById(Integer id);
    // Obtiene todos los proyectos liderados por un líder específico
    List<Project> findByLeaderId(Integer leaderId);
    // Obtiene todos los proyectos en los que un miembro específico está involucrado
    List<Project> findByMemberId(Integer memberId);

    // Cambia el líder de un proyecto
    void changeLeader(Integer projectId, User newLeader); 
}
