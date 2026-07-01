package com.api.proyectmanager.user.application.user;

import com.api.proyectmanager.user.domain.ports.UserRepository;

public class ExistsByEmailService {
    private final UserRepository userRepository; // Repositorio de usuarios (PORTS)

    // Constructor para inyectar el repositorio de usuarios
    public ExistsByEmailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
