package com.api.proyectmanager.project.application.project;

import org.springframework.stereotype.Service;

import com.api.proyectmanager.project.domain.Project;
import com.api.proyectmanager.project.domain.ports.ProjectRepository;
import com.api.proyectmanager.shared.domain.BusinessException;

@Service
public class FindById {
    private final ProjectRepository projectRepository; // Repositorio de proyectos (PORTS)

    public FindById(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Project execute(Integer id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Projecto no encontrado con el ID: " + id));
    }
}
