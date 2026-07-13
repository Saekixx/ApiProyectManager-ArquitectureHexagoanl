package com.api.proyectmanager.user.application.user;

import java.util.Optional;

import com.api.proyectmanager.shared.domain.annotation.UseCase;
import com.api.proyectmanager.user.domain.User;
import com.api.proyectmanager.user.domain.ports.UserRepository;

@UseCase
public class FindByUsername {
    private final UserRepository userRepository; // Repositorio de usuarios (PORTS)

    // Constructor para inyectar el repositorio de usuarios
    public FindByUsername(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
