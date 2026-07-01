package com.api.proyectmanager.user.application.user;

import com.api.proyectmanager.user.domain.User;
import com.api.proyectmanager.user.domain.ports.UserRepository;

public class ActivateByIdService {
    private final UserRepository userRepository; // Repositorio de usuarios (PORTS)

    // Constructor para inyectar el repositorio de usuarios
    public ActivateByIdService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void activateUserById(Integer userId) {
        // Buscar si el usuario existe primero usando el puerto
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Error: El usuario con ID " + userId + " no existe."));

        // Validar si ya estaba activo
        if (user.isActive()) {
            throw new RuntimeException("El usuario ya se encuentra activo en el sistema.");
        }
        
        // Llamar al puerto para activar al usuario por su ID
        userRepository.activateById(userId);
    }
}
