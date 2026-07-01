package com.api.proyectmanager.user.application.user;

import org.springframework.stereotype.Service;

import com.api.proyectmanager.user.domain.Rol;
import com.api.proyectmanager.user.domain.User;
import com.api.proyectmanager.user.domain.ports.RolRepository;
import com.api.proyectmanager.user.domain.ports.UserRepository;

@Service
public class SaveService {
    private final UserRepository userRepository; // Repositorio de usuarios (PORTS)
    private final RolRepository rolRepository; // Repositorio de roles (PORTS)
    private static final String ROL_DEFAULT = "COLABORADOR"; // Nombre del rol por defecto

    // Constructor para inyectar el repositorio de usuarios
    public SaveService(UserRepository userRepository, RolRepository rolRepository) {
        this.userRepository = userRepository;
        this.rolRepository = rolRepository;
    }

    // Método para guardar un usuario
    public User save(User user, Integer rolId) {
        // Validar que el email no sea duplicado
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("El correo electrónico ya está en uso");
        }
        // Rol por defecto si no se proporciona un rolId
        Rol rol;
        // Si se proporciona un rolId, buscar el rol correspondiente, de lo contrario, usar el rol por defecto
        if (rolId != null) {
        rol = rolRepository.findById(rolId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Rol no encontrado"));
        } else {
        rol = rolRepository.findByName(ROL_DEFAULT)
                .orElseThrow(() ->
                        new IllegalArgumentException("Rol COLABORADOR no encontrado"));
        }
        // Asignar el rol al usuario y guardarlo en el repositorio
        user.setRol(rol);
        // Guardar el usuario en el repositorio y devolverlo
        return userRepository.save(user);
    }
}
