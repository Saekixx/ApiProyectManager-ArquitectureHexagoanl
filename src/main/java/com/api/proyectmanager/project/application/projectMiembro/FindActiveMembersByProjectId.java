package com.api.proyectmanager.project.application.projectMiembro;

import java.util.List;

import com.api.proyectmanager.project.domain.ProjectMiembro;
import com.api.proyectmanager.project.domain.ports.ProjectMemberRepository;
import com.api.proyectmanager.shared.domain.annotation.UseCase;

@UseCase
public class FindActiveMembersByProjectId {
    private final ProjectMemberRepository projectMemberRepository; // Repositorio de miembros del proyecto (PORTS)

    // Constructor para inyectar el repositorio de miembros del proyecto
    public FindActiveMembersByProjectId(ProjectMemberRepository projectMemberRepository) {
        this.projectMemberRepository = projectMemberRepository;
    }

    public List<ProjectMiembro> execute(Integer projectId) {
        // Obtenemos la lista de miembros activos del proyecto usando el puerto
        return projectMemberRepository.findActiveMembersByProjectId(projectId);
    }
}
