package com.api.proyectmanager.user.application.user;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.api.proyectmanager.shared.domain.BusinessException;
import com.api.proyectmanager.shared.domain.annotation.UseCase;
import com.api.proyectmanager.user.application.dtos.UserCreateRequest;
import com.api.proyectmanager.user.domain.Rol;
import com.api.proyectmanager.user.domain.User;
import com.api.proyectmanager.user.domain.ports.RolRepository;
import com.api.proyectmanager.user.domain.ports.UserRepository;

@UseCase
public class Save {
    private final UserRepository userRepository; // Repositorio de usuarios (PORTS)
    private final RolRepository rolRepository; // Repositorio de roles (PORTS)
    private static final String ROL_DEFAULT = "COLABORADOR"; // Nombre del rol por defecto
    private final PasswordEncoder passwordEncoder; // Inyección de PasswordEncoder para encriptar contraseñas

    // Constructor para inyectar el repositorio de usuarios
    public Save(UserRepository userRepository, RolRepository rolRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Método para guardar un usuario
    public String save(UserCreateRequest user) {
        // Validar que el email no sea duplicado
        if (userRepository.existsByEmail(user.email())) {
            throw new BusinessException("El correo electrónico ya está en uso");
        }
        // Creamos una instancia de User con los datos proporcionados
        User newUser = new User();
        newUser.setUsername(user.username());
        // Encriptar la contraseña antes de guardarla
        newUser.setPassword(passwordEncoder.encode(user.password()));
        newUser.setFullname(user.fullname());
        newUser.setEmail(user.email());
        // El usuario se crea como activo por defecto
        newUser.setIsActive(true);
        // Rol por defecto si no se proporciona un rolId
        Rol rol;
        // Si se proporciona un rolId, buscar el rol correspondiente, de lo contrario, usar el rol por defecto
        if (user.rolId() != null) {
        rol = rolRepository.findById(user.rolId())
                .orElseThrow(() ->
                        new BusinessException("Rol no encontrado"));
        } else {
        rol = rolRepository.findByName(ROL_DEFAULT)
                .orElseThrow(() ->
                        new BusinessException("Rol COLABORADOR no encontrado"));
        }
        // Asignar el rol al usuario y guardarlo en el repositorio
        newUser.setRol(rol);
        // Guardar el usuario en el repositorio y devolverlo
        userRepository.save(newUser);
        // Devolver un mensaje de éxito
        return "Usuario creado exitosamente";
    }
}
