package com.api.proyectmanager.user.ports;

import java.util.List;
import java.util.Optional;

import com.api.proyectmanager.user.domain.User;

public interface UserRepository {
    // Puerto para guardar un usuario
    User save(User user);
    // Puerto para editar un usuario existente
    void update(User user);
    // Puerto para obtener todos los usuarios
    List<User> findAll();
    // Puerto para activar o desactivar un usuario por su ID
    void activateById(Integer id);

    // Puerto para buscar un usuario por su correo electrónico
    Optional<User> findByEmail(String email);
}
