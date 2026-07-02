package com.api.proyectmanager.project.application.project;

import org.springframework.stereotype.Service;

import com.api.proyectmanager.project.domain.Project;
import com.api.proyectmanager.project.domain.ports.ProjectRepository;
import com.api.proyectmanager.shared.domain.BusinessException;

@Service
public class ToggleActiveById {
    private final ProjectRepository projectRepository; // Repositorio de proyectos (PORTS)

    public ToggleActiveById(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public String execute(Integer projectId) {
        //Buscamos si el proyecto existe
        Project project = projectRepository.findById(projectId)
                            .orElseThrow(() -> new BusinessException("El Proyecto con ID " + projectId + " no existe."));
        // Cambiamos el estado del proyecto
        Boolean newActiveState = project.toggleActive();
        // Guardamos el proyecto con su nuevo estado
        projectRepository.save(project);
        // Retornamos un mensaje indicando si el proyecto fue activado o desactivado
        if (newActiveState) {
            return "Proyecto con el ID " + projectId + " activado correctamente.";
        } else {
            return "Proyecto con el ID " + projectId + " desactivado correctamente.";
        }
    }
}
