package com.api.proyectmanager.project.application.project;

import com.api.proyectmanager.project.application.dtos.ProjectUpdate;
import com.api.proyectmanager.project.domain.Project;
import com.api.proyectmanager.project.domain.ports.ProjectRepository;
import com.api.proyectmanager.shared.domain.BusinessException;
import com.api.proyectmanager.shared.domain.annotation.UseCase;

@UseCase
public class Update {
    private final ProjectRepository projectRepository; // Repositorio de proyectos (PORTS)

    public Update(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public void execute(Integer projectId, ProjectUpdate updated) {
        // Validar que el proyecto exista en la base de datos
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new BusinessException("El proyecto con ID " + projectId + " no existe."));
        // Actualizar los campos del proyecto con los valores proporcionados en ProjectUpdate
        project.setName(updated.name());
        project.setDescription(updated.description());
        // Guardamos los cambios en el repositorio usando el puerto
        projectRepository.save(project);
    }
}
