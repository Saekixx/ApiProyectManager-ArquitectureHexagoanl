package com.api.proyectmanager.project.application.dtos;

public record ProjectUpdate(
    String name,
    String description
) {
    // Constructor para validar los campos
    public ProjectUpdate {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("El nombre del proyecto no puede estar vacío");
        }
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("La descripción del proyecto no puede estar vacía");
        }
    }
}