package com.api.proyectmanager.project.application.projectMiembro;

import java.util.List;

import org.springframework.stereotype.Service;

import com.api.proyectmanager.project.domain.ProjectMiembro;
import com.api.proyectmanager.project.domain.ports.ProjectMemberRepository;
import com.api.proyectmanager.project.domain.ports.ProjectRepository;
import com.api.proyectmanager.shared.domain.BusinessException;

@Service
public class FindActiveMembersByProjectId {
    private final ProjectMemberRepository projectMemberRepository; // Repositorio de miembros del proyecto (PORTS)
    private final ProjectRepository projectRepository; // Repositorio de proyectos (PORTS)

    // Constructor para inyectar el repositorio de miembros del proyecto
    public FindActiveMembersByProjectId(ProjectMemberRepository projectMemberRepository, ProjectRepository projectRepository) {
        this.projectMemberRepository = projectMemberRepository;
        this.projectRepository = projectRepository;
    }

    public List<ProjectMiembro> execute(Integer projectId) {
        // Validamos que el proyecto exista
        projectRepository.findById(projectId)
            .orElseThrow(() -> new BusinessException("Proyecto no encontrado con ID: " + projectId));
        // Obtenemos la lista de miembros activos del proyecto usando el puerto
        return projectMemberRepository.findActiveMembersByProjectId(projectId);
    }
}
