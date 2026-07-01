package com.api.proyectmanager.user.application.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.api.proyectmanager.user.domain.User;
import com.api.proyectmanager.user.domain.ports.UserRepository;

@Service
public class UpdateService {
    private final UserRepository userRepository; // Repositorio de usuarios (PORTS)
    private final PasswordEncoder passwordEncoder; // Inyección de PasswordEncoder para encriptar contraseñas

    // Constructor para inyectar el repositorio de usuarios
    public UpdateService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Método para actualizar un usuario
    public void execute(Integer id, User userCambios) {
        // Validamos que el usuario exista antes de actualizarlo
        User userExistente = userRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("User with ID " + id + " does not exist."));
        // Si se envia una contraseña nueva, la encriptamos antes de actualizarla
        if (userCambios.getPassword() != null && !userCambios.getPassword().isEmpty()) {
            userCambios.setPassword(passwordEncoder.encode(userCambios.getPassword()));
        }
        // Validamos campos que no se han enviado para mantener los datos existentes
        userExistente.actualizarDatos(userCambios);
        // Guardamos los cambios en el repositorio usando el puerto
        userRepository.update(userExistente);
    }
}
