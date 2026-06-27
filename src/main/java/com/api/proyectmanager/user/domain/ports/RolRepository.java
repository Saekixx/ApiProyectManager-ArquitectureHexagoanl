package com.api.proyectmanager.user.domain.ports;

import java.util.List;
import java.util.Optional;

import com.api.proyectmanager.user.domain.Rol;

public interface RolRepository {
    // Puerto para buscar un rol por su ID
    Optional<Rol> findById(Integer id);
    // Puerto para buscar un rol por su nombre
    Optional<Rol> findByName(String name);
    // Puerto para listar todos los roles
    List<Rol> findAll(); 
}
