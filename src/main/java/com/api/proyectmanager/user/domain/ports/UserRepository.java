package com.api.proyectmanager.user.domain.ports;

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

    // Puerto para obtener un usuario por su ID
    Optional<User> findById(Integer id);
    // Puerto para buscar un usuario por su correo electrónico
    Optional<User> findByEmail(String email);
    // Puerto para verificar si un correo electrónico ya existe
    Boolean existsByEmail(String email);
    // Puerto para buscar un usuario por su nombre de usuario
    Optional<User> findByUsername(String username);
    // Puerto para verificar si un nombre de usuario ya existe
    Boolean existsByUsername(String username);
    // Puerto para cambiar de rol a un usuario por su ID
    void changeRoleById(Integer userId, Integer rolId);
}
