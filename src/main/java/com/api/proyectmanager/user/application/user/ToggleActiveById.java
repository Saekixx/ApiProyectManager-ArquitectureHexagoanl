package com.api.proyectmanager.user.application.user;

import com.api.proyectmanager.shared.domain.BusinessException;
import com.api.proyectmanager.shared.domain.annotation.UseCase;
import com.api.proyectmanager.user.domain.User;
import com.api.proyectmanager.user.domain.ports.UserRepository;

@UseCase
public class ToggleActiveById {
    private final UserRepository userRepository;

    public ToggleActiveById(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String execute(Integer id) {
        // Buscamos el usuario por su ID, si no existe lanzamos una excepción
        User user = userRepository.findById(id)
                .orElseThrow(() -> new BusinessException("El usuario con ID " + id + " no existe."));

        // Cambiamos el estado activo del usuario (si estaba activo, lo desactivamos y viceversa)
        Boolean newActiveState = user.toggleActive();
        // Le decimos al puerto que guarde al usuario con su nuevo estado
        userRepository.save(user);
        // Retornamos un mensaje indicando si el usuario fue activado o desactivado
        return newActiveState 
            ? "Usuario con el ID " + id + " activado correctamente." 
            : "Usuario con el ID " + id + " desactivado correctamente.";
    }
}
