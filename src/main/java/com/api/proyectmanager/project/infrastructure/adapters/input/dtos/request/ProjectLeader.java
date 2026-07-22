package com.api.proyectmanager.project.infrastructure.adapters.input.dtos.request;

public record ProjectLeader(
    Integer projectId,
    Integer newLeaderId
) {
    public ProjectLeader {
        if (projectId == null) {
            throw new IllegalArgumentException("El ID del proyecto no puede ser nulo");
        }
        if (newLeaderId == null) {
            throw new IllegalArgumentException("El ID del nuevo líder no puede ser nulo");
        }
    }
}
