package com.api.proyectmanager.project.application.projectMiembro;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.proyectmanager.project.domain.ProjectMiembro;
import com.api.proyectmanager.project.domain.ports.ProjectMemberRepository;
import com.api.proyectmanager.project.domain.ports.ProjectRepository;
import com.api.proyectmanager.shared.domain.BusinessException;

@Service
public class FindPastMembersByProjectId {
    private final ProjectMemberRepository projectMemberRepository; // Repositorio para manejar la persistencia de miembros del proyecto (PORTS)
    private final ProjectRepository projectRepository; // Repositorio para manejar la persistencia de proyectos (PORTS)

    // Constructor que inyecta el repositorio de miembros del proyecto
    public FindPastMembersByProjectId(ProjectMemberRepository projectMemberRepository, ProjectRepository projectRepository) {
        this.projectMemberRepository = projectMemberRepository;
        this.projectRepository = projectRepository;
    }

    @Transactional(readOnly = true) // 👈 Optimiza el rendimiento para consultas de solo lectura
    public List<ProjectMiembro> execute(Integer projectId) {
        // Validar que el proyecto exista
        projectRepository.findById(projectId)
                .orElseThrow(() -> new BusinessException("El proyecto con ID " + projectId + " no existe."));
        // Llamar al puerto para obtener la lista de antiguos miembros
        return projectMemberRepository.findPastMembersByProjectId(projectId);
    }
}
