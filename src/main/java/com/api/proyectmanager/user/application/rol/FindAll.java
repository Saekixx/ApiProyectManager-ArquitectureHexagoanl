package com.api.proyectmanager.user.application.rol;

import java.util.List;

import org.springframework.stereotype.Service;

import com.api.proyectmanager.user.domain.Rol;
import com.api.proyectmanager.user.domain.ports.RolRepository;

@Service("rolFindAll")
public class FindAll {
    private final RolRepository rolRepository; // Repositorio de roles (PORTS)

    // Constructor para inyectar el repositorio de roles
    public FindAll(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    // Método para listar todos los roles
    public List<Rol> findAll() {
        return rolRepository.findAll();
    }

}
