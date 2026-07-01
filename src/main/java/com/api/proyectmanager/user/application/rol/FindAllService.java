package com.api.proyectmanager.user.application.rol;

import java.util.List;

import com.api.proyectmanager.user.domain.Rol;
import com.api.proyectmanager.user.domain.ports.RolRepository;

public class FindAllService {
    private final RolRepository rolRepository; // Repositorio de roles (PORTS)

    // Constructor para inyectar el repositorio de roles
    public FindAllService(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    // Método para listar todos los roles
    public List<Rol> findAll() {
        return rolRepository.findAll();
    }

}
