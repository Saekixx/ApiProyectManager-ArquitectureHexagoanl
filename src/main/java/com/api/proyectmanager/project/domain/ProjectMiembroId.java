package com.api.proyectmanager.project.domain;

import java.io.Serializable;

import jakarta.persistence.Embeddable;

@Embeddable
public record ProjectMiembroId(Integer projectId, Integer userId) implements Serializable {
    // Este record se utiliza como clave primaria compuesta para la entidad ProjectMiembro
}
