package com.api.proyectmanager.user.application.rol;

import com.api.proyectmanager.user.domain.ports.RolRepository;

public class FindByNameService {
    private final RolRepository rolRepository; // Repositorio de roles (PORTS)

    // Constructor para inyectar el repositorio de roles
    public FindByNameService(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    // Método para encontrar un rol por su nombre
    public boolean findByName(String name) {
        return rolRepository.findByName(name).isPresent();
    }
}
