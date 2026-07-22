package com.api.proyectmanager.project.application.dtos;

import java.util.Set;

import com.api.proyectmanager.shared.domain.ValidationException;

public record ProjectRequest(
    String name,
    String description,
    Integer leaderId,
    Set<Integer> memberIds
) {
    // Constructor para validar los campos
    public ProjectRequest {
        if (name == null || name.isBlank()) {
            throw new ValidationException("El nombre del proyecto no puede estar vacío");
        }
        if (description == null || description.isBlank()) {
            throw new ValidationException("La descripción del proyecto no puede estar vacía");
        }
        if (leaderId == null) {
            throw new ValidationException("El ID del líder del proyecto no puede ser nulo");
        }
        if (memberIds == null) {
            throw new ValidationException("La lista de miembros del proyecto no puede ser nula");
        }
    }
}
