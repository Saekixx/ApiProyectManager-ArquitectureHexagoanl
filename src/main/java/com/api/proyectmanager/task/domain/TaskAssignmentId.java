package com.api.proyectmanager.task.domain;

import java.io.Serializable;

import jakarta.persistence.Embeddable;

@Embeddable
public record TaskAssignmentId(Integer taskId, Integer userId) implements Serializable {
    // Este record se utiliza como clave primaria compuesta para la entidad TaskAssignment
}
