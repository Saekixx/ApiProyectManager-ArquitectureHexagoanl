package com.api.proyectmanager.user.application.user;

import java.util.List;

import com.api.proyectmanager.shared.domain.annotation.UseCase;
import com.api.proyectmanager.user.domain.User;
import com.api.proyectmanager.user.domain.ports.UserRepository;

@UseCase
public class FindAll {
    private final UserRepository userRepository; // Repositorio de usuarios (PORTS)

    // Constructor para inyectar el repositorio de usuarios
    public FindAll(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }
}
