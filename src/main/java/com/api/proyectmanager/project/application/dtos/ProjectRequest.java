package com.api.proyectmanager.project.application.dtos;

import java.util.Set;

public record ProjectRequest(
    String name,
    String description,
    Integer leaderId,
    Set<Integer> memberIds
) {
    // Constructor para validar los campos
    public ProjectRequest {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("El nombre del proyecto no puede estar vacío");
        }
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("La descripción del proyecto no puede estar vacía");
        }
        if (leaderId == null) {
            throw new IllegalArgumentException("El ID del líder del proyecto no puede ser nulo");
        }
        if (memberIds == null) {
            throw new IllegalArgumentException("La lista de miembros del proyecto no puede ser nula");
        }
    }
}
