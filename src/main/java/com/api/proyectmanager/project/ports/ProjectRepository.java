package com.api.proyectmanager.project.ports;

import java.util.List;

import com.api.proyectmanager.project.domain.Project;

public interface ProjectRepository {
    // Puerto para guardar un proyecto
    Project save(Project project);

    // Puerto para editar un proyecto existente
    void update(Project project);

    // Puerto para activar o desactivar un proyecto por su ID
    void activateById(Integer id);

    // Puerto para listar todos los proyectos
    List<Project> findAll();

    // Puerto para listar todos los proyectos por líder
    List<Project> findByLeaderId(Integer leaderId);

    // Puerto para obtener un proyecto por su ID
    Project findById(Integer id);
}
