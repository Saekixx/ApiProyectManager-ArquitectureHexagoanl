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
        // Llamamos al puerto para buscar el usuario por su ID
        return userRepository.findById(id)
                .map(user -> user.getIsActive()) // Si existe, extraemos su boolean interno
                .orElse(false);                  // Si no existe la fila, devolvemos false
    }
}
