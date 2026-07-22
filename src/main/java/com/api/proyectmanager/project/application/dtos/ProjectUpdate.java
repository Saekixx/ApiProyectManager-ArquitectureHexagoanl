package com.api.proyectmanager.project.application.dtos;

import com.api.proyectmanager.shared.domain.ValidationException;

public record ProjectUpdate(
    String name,
    String description
) {
    // Constructor para validar los campos
    public ProjectUpdate {
        if (name == null || name.isBlank()) {
            throw new ValidationException("El nombre del proyecto no puede estar vacío");
        }
        if (description == null || description.isBlank()) {
            throw new ValidationException("La descripción del proyecto no puede estar vacía");
        }
    }
}