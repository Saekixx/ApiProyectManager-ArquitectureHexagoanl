package com.api.proyectmanager.user.application.user;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.api.proyectmanager.shared.domain.BusinessException;
import com.api.proyectmanager.shared.domain.annotation.UseCase;
import com.api.proyectmanager.user.application.dtos.UserUpdateRequest;
import com.api.proyectmanager.user.domain.User;
import com.api.proyectmanager.user.domain.ports.UserRepository;

@UseCase
public class Update {
    private final UserRepository userRepository; // Repositorio de usuarios (PORTS)
    private final PasswordEncoder passwordEncoder; // Inyección de PasswordEncoder para encriptar contraseñas

    // Constructor para inyectar el repositorio de usuarios
    public Update(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Método para actualizar un usuario
    public void execute(Integer id, UserUpdateRequest update) {
        // Validamos que el usuario exista antes de actualizarlo
        User userExistente = userRepository.findById(id)
            .orElseThrow(() -> new BusinessException("Usuario con ID " + id + " no existe."));
        // Si se envia una contraseña nueva, la encriptamos antes de actualizarla
        if (update.password() != null && !update.password().isEmpty()) {
            userExistente.setPassword(passwordEncoder.encode(update.password()));
        }
        // Validamos campos que no se han enviado para mantener los datos existentes
        userExistente.actualizarDatosGenerales(update.username(), update.email(), update.fullname(), userExistente.getPassword());
        // Guardamos los cambios en el repositorio usando el puerto
        userRepository.save(userExistente);
    }
}
