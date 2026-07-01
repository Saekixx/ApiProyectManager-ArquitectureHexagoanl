package com.api.proyectmanager.project.domain.ports;

import java.util.List;
import java.util.Optional;

import com.api.proyectmanager.project.domain.Project;

public interface ProjectRepository {
    // Puerto para guardar un proyecto
    Project save(Project project);
    // Puerto para editar un proyecto existente
    void update(Project project);
    // Puerto para activar o desactivar un proyecto por su ID
    Boolean toggleActive(Integer id);
    // Cambiar el líder de un proyecto por su ID
    void changeLeader(Integer projectId, Integer newLeaderId);
    // Puerto para listar todos los proyectos
    List<Project> findAll();

    // Puerto para obtener un proyecto por su ID
    Optional<Project> findById(Integer id);
    // Puerto para listar todos los proyectos por líder
    Optional<List<Project>> findByLeaderId(Integer leaderId);
    // Puerto para listar todos los proyectos por miembro
    Optional<List<Project>> findByMemberId(Integer memberId);
}
