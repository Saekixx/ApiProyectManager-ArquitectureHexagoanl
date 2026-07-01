package com.api.proyectmanager.project.application.project;

import org.springframework.stereotype.Service;

import com.api.proyectmanager.project.domain.Project;
import com.api.proyectmanager.project.domain.ports.ProjectRepository;
import com.api.proyectmanager.shared.domain.BusinessException;

@Service
public class Update {
    private final ProjectRepository projectRepository; // Repositorio de proyectos (PORTS)

    public Update(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public void execute(Integer id, Project updatedProject) {
        // Validar que el proyecto exista en la base de datos
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new BusinessException("El proyecto con ID " + id + " no existe."));
        // Validamos campos que no se han enviado para mantener los datos existentes
        project.actualizarDatos(updatedProject.getName(),updatedProject.getDescription());
        // Guardamos los cambios en el repositorio usando el puerto
        projectRepository.save(project);
    }
}
