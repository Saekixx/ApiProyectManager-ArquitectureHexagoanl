package com.api.proyectmanager.project.application.projectMiembro;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.proyectmanager.project.domain.ports.ProjectMemberRepository;


@Service
public class FindActiveMembersByProjectId {
    private final ProjectMemberRepository projectMemberRepository; // Repositorio para manejar la persistencia de miembros del proyecto (PORTS)

    // Constructor que inyecta el repositorio de miembros del proyecto
    public FindActiveMembersByProjectId(ProjectMemberRepository projectMemberRepository) {
        this.projectMemberRepository = projectMemberRepository;
    }

    @Transactional(readOnly = true)
    public Boolean execute(Integer projectId, Integer userId) {
        // Llamamos al puerto para buscar si el usuario ya existe en el proyecto
        return projectMemberRepository.findByProjectIdAndUserId(projectId, userId)
                .map(miembro -> miembro.getIsActive()) // Si existe, extraemos su boolean interno
                .orElse(false);                        // Si no existe la fila, devolvemos false
    }
}
