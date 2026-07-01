package com.api.proyectmanager.project.application.projectMiembro;

import java.util.List;

import com.api.proyectmanager.project.domain.ProjectMiembro;
import com.api.proyectmanager.project.domain.ports.ProjectMemberRepository;
import com.api.proyectmanager.project.domain.ports.ProjectRepository;

public class FindAllMembersByProjectId {
    private final ProjectMemberRepository projectMemberRepository;
    private final ProjectRepository projectRepository;

    public FindAllMembersByProjectId(ProjectMemberRepository projectMemberRepository, ProjectRepository projectRepository) {
        this.projectMemberRepository = projectMemberRepository;
        this.projectRepository = projectRepository;
    }

    public List<ProjectMiembro> execute(Integer projectId) {
        // Validar que el proyecto exista
        projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("El proyecto con ID " + projectId + " no existe."));
        // Llamar al puerto para obtener la lista de todos los miembros
        return projectMemberRepository.findAllMembersByProjectId(projectId);
    }
}
