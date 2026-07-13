package com.api.proyectmanager.user.application.dtos;

import com.api.proyectmanager.shared.domain.ValidationException;

public record UserCreateRequest(
    String username,
    String password,
    String fullname,
    String email,
    Integer rolId // Puede ser null si no se proporciona un rol, por defecto rol COLABORADOR
){
    // Constructor para validar que los campos no sean nulos o vacíos
    public UserCreateRequest {
        if (username == null || username.trim().isEmpty()) {
            throw new ValidationException("El nombre de usuario no puede ser nulo o vacío.");
        }
        if (password == null || password.length() < 6) {
            throw new ValidationException("La contraseña debe tener al menos 6 caracteres.");
        }
        if (fullname == null || fullname.trim().isEmpty()) {
            throw new ValidationException("El nombre completo no puede ser nulo o vacío.");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new ValidationException("El correo electrónico no puede ser nulo o vacío.");
        }
        // Validación básica del formato del correo electrónico
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new ValidationException("El correo electrónico no tiene un formato válido.");
        }
    }
}
