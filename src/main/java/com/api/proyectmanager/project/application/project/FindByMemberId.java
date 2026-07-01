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
        return projectRepository.findByMemberId(memberId)
                .orElseThrow(() -> new BusinessException("Projectos no encontrados para el ID de miembro: " + memberId));
    }
}
