package com.api.proyectmanager.project.application.project;

import java.util.List;

import com.api.proyectmanager.project.domain.Project;
import com.api.proyectmanager.project.domain.ports.ProjectRepository;
import com.api.proyectmanager.shared.domain.BusinessException;
import com.api.proyectmanager.shared.domain.annotation.UseCase;

@UseCase
public class FindByMemberId {
    private final ProjectRepository projectRepository; // Repositorio de proyectos (PORTS)

    public FindByMemberId(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public List<Project> execute(Integer memberId) {
        List<Project> projects = projectRepository.findByMemberId(memberId);
        if (projects.isEmpty()) {
            throw new BusinessException("No se encontraron proyectos para el miembro con ID: " + memberId);
        }
        return projects;
    }
}
