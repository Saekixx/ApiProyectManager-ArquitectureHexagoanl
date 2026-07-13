package com.api.proyectmanager.user.application.user;

import com.api.proyectmanager.shared.domain.BusinessException;
import com.api.proyectmanager.shared.domain.annotation.UseCase;
import com.api.proyectmanager.user.domain.User;
import com.api.proyectmanager.user.domain.ports.UserRepository;

@UseCase
public class IdFindByEmail {
    private final UserRepository userRepository; // Repositorio de usuarios (PORTS)

    // Constructor para inyectar el repositorio de usuarios
    public IdFindByEmail(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Integer execute(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException("Usuario no encontrado con el email: " + email));
        return user.getId();
    }
}
