package com.api.proyectmanager.auth.infrastructure.adapters.input.dto;

public record RegisterRequest(
    String username,
    String password,
    String fullname,
    String email
) {}