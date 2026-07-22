package com.api.proyectmanager.project.application.projectMiembro;

import java.util.List;

import com.api.proyectmanager.project.application.dtos.MemberDTO;
import com.api.proyectmanager.project.domain.Project;
import com.api.proyectmanager.project.domain.ProjectMiembro;
import com.api.proyectmanager.project.domain.ports.ProjectMemberRepository;
import com.api.proyectmanager.project.domain.ports.ProjectRepository;
import com.api.proyectmanager.shared.domain.annotation.UseCase;

@UseCase
public class FindAllMembersByProjectId {
    private final ProjectMemberRepository projectMemberRepository; // Repositorio de miembros del proyecto (PORTS)
    private final ProjectRepository projectRepository; // Repositorio de proyectos (PORTS)

    // Constructor para inyectar el repositorio de miembros del proyecto
    public FindAllMembersByProjectId(ProjectMemberRepository projectMemberRepository, ProjectRepository projectRepository) {
        this.projectMemberRepository = projectMemberRepository;
        this.projectRepository = projectRepository;
    }

    public List<MemberDTO> execute(Integer projectId) {
        // Validar si el proyecto existe
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("El proyecto con ID " + projectId + " no existe."));
        // Obtenemos el id del leader del proyecto (evitamos NPE si el leader es null)
        Integer leaderId = (project.getLeader() != null) ? project.getLeader().getId() : null;
        // Obtenemos los miembros del proyecto
        List<ProjectMiembro> projectMembers = projectMemberRepository.findAllMembersByProjectId(projectId);
        // Convertimos los miembros del proyecto a DTOs
        return projectMembers.stream()
                    .map(member -> {
                        // Verificamos si el miembro es el líder del proyecto
                        boolean isLeader = leaderId != null && leaderId.equals(member.getUser().getId());

                        // Obtenemos el rol de forma segura
                        String roleName = (member.getUser().getRol() != null) 
                                ? member.getUser().getRol().getName() 
                                : "SIN ROL";

                        return new MemberDTO(
                                member.getUser().getId(),
                                member.getUser().getFullname(),
                                member.getUser().getEmail(),
                                roleName,
                                isLeader,
                                member.getIsActive(),
                                member.getJoinedAt(),
                                member.getExitedAt()
                        );
                    })
                    .toList();
    }
}
