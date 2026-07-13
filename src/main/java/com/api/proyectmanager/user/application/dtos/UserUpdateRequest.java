package com.api.proyectmanager.user.application.dtos;

import com.api.proyectmanager.shared.domain.ValidationException;

public record UserUpdateRequest(
    String username,
    String password,
    String fullname,
    String email,
    Integer rolId // Puede ser null si no se proporciona un rol, por defecto rol COL
) {
    // Constructor para validar que los campos no sean nulos o vacíos
    public UserUpdateRequest {
        if (username == null || username.isBlank()) {
            throw new ValidationException("El nombre de usuario es obligatorio.");
        }
        if (fullname == null || fullname.isBlank()) {
            throw new ValidationException("El nombre completo es obligatorio.");
        }
        if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new ValidationException("El formato del correo electrónico no es válido.");
        }
        if (rolId == null) {
            throw new ValidationException("El ID del rol es obligatorio.");
        }
        
        // La contraseña solo se valida si el frontend envió caracteres para cambiarla
        if (password != null && !password.isBlank() && password.length() < 6) {
            throw new ValidationException("La nueva contraseña debe tener al menos 6 caracteres.");
        }
    }
}
