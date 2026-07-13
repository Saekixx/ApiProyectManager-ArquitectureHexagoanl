package com.api.proyectmanager.project.infrastructure.adapters.input.dtos.response;

import java.time.LocalDateTime;

import com.api.proyectmanager.project.domain.Project;

public record ProjectListDTO(
    Integer id,
    String name,
    String description,
    LeaderDTO leader,
    Boolean isActive,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
    // Método estático para convertir un objeto de dominio Project a ProjectListDTO
    public static ProjectListDTO fromDomain(Project project) {
        return new ProjectListDTO(
            project.getId(),
            project.getName(),
            project.getDescription(),
            new LeaderDTO(
                project.getLeader().getId(),
                project.getLeader().getUsername(),
                project.getLeader().getFullname(),
                project.getLeader().getEmail()
            ),
            project.isActive(),
            project.getCreatedAt(),
            project.getUpdatedAt()
        );
    }
}
