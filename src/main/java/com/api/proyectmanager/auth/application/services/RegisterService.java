package com.api.proyectmanager.auth.application.services;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.api.proyectmanager.auth.domain.ports.RegisterUseCase;
import com.api.proyectmanager.auth.infrastructure.adapters.input.dto.RegisterRequest;
import com.api.proyectmanager.shared.domain.annotation.UseCase;
import com.api.proyectmanager.user.domain.Rol;
import com.api.proyectmanager.user.domain.User;
import com.api.proyectmanager.user.domain.ports.RolRepository;
import com.api.proyectmanager.user.domain.ports.UserRepository;

@UseCase
public class RegisterService implements RegisterUseCase {
    private final UserRepository userRepository; // Puerto del modulo user
    private final RolRepository rolRepository; // Puerto del modulo rol
    private final PasswordEncoder passwordEncoder; // Encriptador de contraseñas

    // Inyeccion de dependencias a través del constructor
    public RegisterService(UserRepository userRepository, RolRepository rolRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void register(RegisterRequest request) {
        // Validar si el email ya está registrado en la base de datos
        if (userRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("El correo electrónico ya está registrado.");
        }
        //Buscar el rol por defecto para el usuario nuevo (por ejemplo, "COLABORADOR") y asignarlo a la entidad User
        Rol defaultRol = rolRepository.findByName("COLABORADOR")
            .orElseThrow(() -> new IllegalStateException("Error interno: El rol COLABORADOR no está configurado en el sistema."));

        // Transmutacion pasamos los datos del DTO a la entidad User
        User newUser = new User();
        newUser.setUsername(request.username());
        newUser.setFullname(request.fullname());
        newUser.setEmail(request.email());
        newUser.setRol(defaultRol); // Asignamos el rol por defecto al usuario
        // Encriptamos la contraseña antes de guardarla
        newUser.setPassword(passwordEncoder.encode(request.password()));
        // Guardamos el nuevo usuario usando el puerto limpio de persistencia del módulo User
        userRepository.save(newUser);
    }
}
