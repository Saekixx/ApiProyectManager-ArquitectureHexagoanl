package com.api.proyectmanager.project.application.project;

import java.util.List;

import com.api.proyectmanager.project.domain.Project;
import com.api.proyectmanager.project.domain.ports.ProjectRepository;
import com.api.proyectmanager.shared.domain.annotation.UseCase;

@UseCase
public class FindAll {
    private final ProjectRepository projectRepository; // Repositorio de proyectos (PORTS)

    public FindAll(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public List<Project> findAll() {
        return projectRepository.findAll();
    }
}
