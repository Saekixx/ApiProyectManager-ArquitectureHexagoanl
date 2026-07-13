package com.api.proyectmanager.project.infrastructure.adapters.input.dtos.response;

public record LeaderDTO(
    Integer id,
    String username,
    String fullName,
    String email
) {}
