package com.api.proyectmanager.user.application.user;

import org.springframework.stereotype.Service;

import com.api.proyectmanager.user.domain.ports.UserRepository;

@Service
public class IsActiveById {
    private final UserRepository userRepository; // Repositorio de usuarios (PORTS)

    // Constructor para inyectar el repositorio de usuarios
    public IsActiveById(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Boolean isActiveById(Integer id) {
        // Validar que el usuario exista antes de verificar si está activo
        userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("El usuario con ID " + id + " no existe."));
        // Verificar si el usuario está activo en el repositorio usando el puerto
        return userRepository.isActiveById(id);
    }
}
