package com.api.proyectmanager.user.application.user;

import com.api.proyectmanager.shared.domain.BusinessException;
import com.api.proyectmanager.shared.domain.annotation.UseCase;
import com.api.proyectmanager.user.domain.ports.RolRepository;
import com.api.proyectmanager.user.domain.ports.UserRepository;
import com.api.proyectmanager.user.application.dtos.ChangeRolRequest;
import com.api.proyectmanager.user.domain.Rol;
import com.api.proyectmanager.user.domain.User;

@UseCase
public class ChangeRoleById {
    private final UserRepository userRepository; // Repositorio de usuarios (PORTS)
    private final RolRepository rolRepository; // Repositorio de roles (PORTS)

    // Constructor para inyectar el repositorio de usuarios
    public ChangeRoleById(UserRepository userRepository, RolRepository rolRepository) {
        this.userRepository = userRepository;
        this.rolRepository = rolRepository;
    }

    // Método para cambiar el rol de un usuario por su ID
    public void execute(Integer userId, ChangeRolRequest request) { ;
        // Validamos que el usuario exista antes de cambiar su rol
        User userExistente = userRepository.findById(userId)
            .orElseThrow(() -> new BusinessException("Usuario con ID " + userId + " no existe."));
        // Validamos que el rol exista antes de asignarlo al usuario
        Rol rolExistente = rolRepository.findById(request.rolId())
            .orElseThrow(() -> new BusinessException("Rol con ID " + request.rolId() + " no existe."));
        // Cambiamos el rol del usuario
        userExistente.setRol(rolExistente);
        // Guardamos los cambios en el repositorio usando el puerto
        userRepository.save(userExistente);
    }
}
