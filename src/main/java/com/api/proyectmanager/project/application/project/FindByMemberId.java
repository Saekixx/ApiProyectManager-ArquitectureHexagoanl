package com.api.proyectmanager.project.application.project;

import java.util.List;

import org.springframework.stereotype.Service;

import com.api.proyectmanager.project.domain.Project;
import com.api.proyectmanager.project.domain.ports.ProjectRepository;
import com.api.proyectmanager.shared.domain.BusinessException;

@Service
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
