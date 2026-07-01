package com.api.proyectmanager.user.application.user;

import com.api.proyectmanager.user.domain.User;
import com.api.proyectmanager.user.domain.ports.UserRepository;

public class UpdateService {
    private final UserRepository userRepository; // Repositorio de usuarios (PORTS)

    // Constructor para inyectar el repositorio de usuarios
    public UpdateService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Método para actualizar un usuario
    public void update(User user, Integer id) {
        // Verificar si el usuario con el ID proporcionado existe
        if (userRepository.findById(id).isPresent()) {
            // Si existe, actualizar el usuario
            user.setId(id); // Asegurarse de que el ID del usuario sea el correcto
            userRepository.update(user);
        } else {
            // Si no existe, lanzar una excepción o manejar el caso según sea necesario
            throw new IllegalArgumentException("User with ID " + id + " does not exist.");
        }
    }
}
