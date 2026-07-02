package com.api.proyectmanager.project.application.project;

import java.util.List;

import org.springframework.stereotype.Service;

import com.api.proyectmanager.project.domain.Project;
import com.api.proyectmanager.project.domain.ports.ProjectRepository;
import com.api.proyectmanager.shared.domain.BusinessException;

@Service
public class FindByLeaderId {
    private final ProjectRepository projectRepository; // Repositorio de proyectos (PORTS)

    public FindByLeaderId(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public List<Project> execute(Integer leaderId) {
        List<Project> projects = projectRepository.findByLeaderId(leaderId);
        if (projects.isEmpty()) {
            throw new BusinessException("No se encontraron proyectos para el líder con ID: " + leaderId);
        }
        return projects;
    }
}
