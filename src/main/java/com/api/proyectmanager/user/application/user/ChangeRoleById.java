package com.api.proyectmanager.user.application.user;

import org.springframework.stereotype.Service;

import com.api.proyectmanager.shared.domain.BusinessException;
import com.api.proyectmanager.user.domain.ports.RolRepository;
import com.api.proyectmanager.user.domain.ports.UserRepository;

@Service
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
        // Validar que el rol exista antes de cambiarlo
        rolRepository.findById(rolId)
                .orElseThrow(() -> new BusinessException("Rol no encontrado"));
        // Cambiar el rol del usuario por su ID usando el puerto
        userRepository.changeRoleById(userId, rolId);
    }
}
