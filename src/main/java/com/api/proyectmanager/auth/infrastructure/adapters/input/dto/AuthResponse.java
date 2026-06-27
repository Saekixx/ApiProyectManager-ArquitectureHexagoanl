package com.api.proyectmanager.auth.infrastructure.adapters.input.dto;

public record AuthResponse(
    String token,
    String email,
    String username
) {}