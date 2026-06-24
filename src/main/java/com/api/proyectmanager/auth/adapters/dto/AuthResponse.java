package com.api.proyectmanager.auth.adapters.dto;

public record AuthResponse(
    String token,
    String email,
    String username
) {}