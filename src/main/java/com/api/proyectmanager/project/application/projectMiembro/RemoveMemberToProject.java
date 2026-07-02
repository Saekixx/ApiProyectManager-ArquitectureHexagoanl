package com.api.proyectmanager.project.application.projectMiembro;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.api.proyectmanager.project.domain.ProjectMiembro;
import com.api.proyectmanager.project.domain.ports.ProjectMemberRepository;
import com.api.proyectmanager.shared.domain.BusinessException;

@Service
public class RemoveMemberToProject {
    private final ProjectMemberRepository projectMemberRepository; // Repositorio para manejar la persistencia de miembros del proyecto (PORTS)

    // Constructor que inyecta el repositorio de miembros del proyecto
    public RemoveMemberToProject(ProjectMemberRepository projectMemberRepository) {
        this.projectMemberRepository = projectMemberRepository;
    }

    public String execute(Integer projectId, Integer userId) {
        // Buscamos la relación usando el Optional del puerto
        ProjectMiembro projectMiembro = projectMemberRepository.findByProjectIdAndUserId(projectId, userId)
                .orElseThrow(() -> new BusinessException("El usuario con ID " + userId + " no pertenece al proyecto con ID " + projectId + "."));
        // Regla de Negocio: Validar si ya estaba inactivo en el histórico
        if (!projectMiembro.getIsActive()) {
            throw new BusinessException("El usuario con ID " + userId + " ya se encuentra inactivo (en el histórico) de este proyecto.");
        }
        // Modificamos el estado en el dominio (Desactivación lógica)
        projectMiembro.setIsActive(false);
        // Guardamos la fecha de salida del proyecto
        projectMiembro.setExitedAt(LocalDateTime.now()); 
        // Persistimos el cambio a través del puerto
        // Tu adaptador por dentro recibirá este objeto con isActive = false y ejecutará un UPDATE en la BD
        projectMemberRepository.removeMemberFromProject(projectMiembro);
        // Retornamos un mensaje indicando que el usuario ha sido removido exitosamente del proyecto
        return "El usuario con ID " + userId + " ha sido inactivado exitosamente del proyecto con ID " + projectId + ".";
    }
}
