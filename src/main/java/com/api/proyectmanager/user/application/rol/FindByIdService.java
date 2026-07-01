package com.api.proyectmanager.user.application.rol;

import java.util.Optional;

import com.api.proyectmanager.user.domain.Rol;
import com.api.proyectmanager.user.domain.ports.RolRepository;

public class FindByIdService {
    private final RolRepository rolRepository; // Repositorio de roles (PORTS)

    // Constructor para inyectar el repositorio de roles
    public FindByIdService(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    // Método para encontrar un rol por su ID
    public Optional<Rol> findById(Integer id) {
        return rolRepository.findById(id);
    }
}
