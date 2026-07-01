package com.api.proyectmanager.project.application.projectMiembro;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.proyectmanager.project.domain.ports.ProjectMemberRepository;
import com.api.proyectmanager.project.domain.ports.ProjectRepository;
import com.api.proyectmanager.shared.domain.BusinessException;
import com.api.proyectmanager.user.domain.ports.UserRepository;

@Service
public class IsActiveMemberOfProject {
    private final ProjectMemberRepository projectMemberRepository; // Repositorio para manejar la persistencia de miembros del proyecto (PORTS)
    private final ProjectRepository projectRepository; // Repositorio para manejar la persistencia de proyectos (PORTS)
    private final UserRepository userRepository; // Repositorio para manejar la persistencia de usuarios (PORTS)

    // Constructor que inyecta el repositorio de miembros del proyecto
    public IsActiveMemberOfProject(ProjectMemberRepository projectMemberRepository, ProjectRepository projectRepository, UserRepository userRepository) {
        this.projectMemberRepository = projectMemberRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public Boolean execute(Integer projectId, Integer userId) {
        // Validamos que el proyecto existan
        projectRepository.findById(projectId)
                .orElseThrow(() -> new BusinessException("El proyecto con ID " + projectId + " no existe."));
        // Validamos que el usuario existan
        userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException("El usuario con ID " + userId + " no existe."));
        // Retornamos true si el usuario es miembro activo del proyecto, false si no lo es
        return projectMemberRepository.findByProjectIdAndUserId(projectId, userId)
                .map(miembro -> miembro.getIsActive()) // Si existe, extraemos su boolean interno
                .orElse(false);                        // Si no existe la fila, devolvemos false
    }
}
