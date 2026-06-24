package com.api.proyectmanager.user.ports;

import com.api.proyectmanager.user.domain.User;

public interface AuthRepository {
    // Puerto para buscar un usuario por su nombre de usuario
    User findByUsername(String username);
    // Puerto para verificar si un nombre de usuario ya existe
    boolean existsByUsername(String username);
}
