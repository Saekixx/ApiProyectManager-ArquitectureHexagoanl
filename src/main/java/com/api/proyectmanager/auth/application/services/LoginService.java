package com.api.proyectmanager.auth.application.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.api.proyectmanager.auth.domain.exceptions.InvalidCredentialsException;
import com.api.proyectmanager.auth.domain.ports.LoginUseCase;
import com.api.proyectmanager.auth.infrastructure.adapters.input.dto.AuthResponse;
import com.api.proyectmanager.auth.infrastructure.adapters.input.dto.LoginRequest;
import com.api.proyectmanager.auth.infrastructure.security.jwt.JwtUtil;
import com.api.proyectmanager.user.domain.Rol;
import com.api.proyectmanager.user.domain.User;
import com.api.proyectmanager.user.domain.ports.RolRepository;
import com.api.proyectmanager.user.domain.ports.UserRepository;

@Service
public class LoginService implements LoginUseCase {
    private final UserRepository userRepository; // Puerto del modulo user
    private final RolRepository rolRepository; // Puerto del modulo rol
    private final PasswordEncoder passwordEncoder; // Encriptador de contraseñas
    private final JwtUtil jwtUtil; // Utilidad para generar y validar tokens JWT

    // Inyeccion de dependencias a través del constructor
    public LoginService(UserRepository userRepository, RolRepository rolRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public AuthResponse login(LoginRequest request){
        // Buscamos al usuario usando el puerto limpio de persistencia del módulo User
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new InvalidCredentialsException("El correo o la contraseña son incorrectos."));

        // Validamos si la contraseña en texto plano hace match con el hash de la BD
        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new InvalidCredentialsException("El correo o la contraseña son incorrectos.");
        }

        // Obtenemos el rol del usuario usando el puerto limpio de persistencia del módulo Rol
        Rol rol = rolRepository.findById(user.getRol().getId())
                .orElseThrow(() -> new InvalidCredentialsException("El rol del usuario no es válido."));
        

        // Generamos el token JWT con tu herramienta utilitaria
        String token = jwtUtil.generateToken(user.getEmail(), rol.getName());

        // Retornamos el DTO de respuesta impecable hacia el exterior
        return new AuthResponse(token, user.getEmail(), user.getUsername());
    }
}
