package com.api.proyectmanager.user.application.user;

import org.springframework.stereotype.Service;

import com.api.proyectmanager.shared.domain.BusinessException;
import com.api.proyectmanager.user.domain.ports.RolRepository;
import com.api.proyectmanager.user.domain.ports.UserRepository;

@Service("userChangeRoleById")
public class ChangeRoleById {
    private final UserRepository userRepository; // Repositorio de usuarios (PORTS)
    private final RolRepository rolRepository; // Repositorio de roles (PORTS)

    // Constructor para inyectar el repositorio de usuarios
    public ChangeRoleById(UserRepository userRepository, RolRepository rolRepository) {
        this.userRepository = userRepository;
        this.rolRepository = rolRepository;
    }

    // Método para cambiar el rol de un usuario por su ID
    public void execute(Integer userId, Integer rolId) {
        // Validamos que el usuario exista antes de cambiar su rol
        var userExistente = userRepository.findById(userId)
            .orElseThrow(() -> new BusinessException("Usuario con ID " + userId + " no existe."));
        // Validamos que el rol exista antes de asignarlo al usuario
        var rolExistente = rolRepository.findById(rolId)
            .orElseThrow(() -> new BusinessException("Rol con ID " + rolId + " no existe."));
        // Cambiamos el rol del usuario
        userExistente.setRol(rolExistente);
        // Guardamos los cambios en el repositorio usando el puerto
        userRepository.save(userExistente);
    }
}
