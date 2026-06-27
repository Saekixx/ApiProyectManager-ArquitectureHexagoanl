package com.api.proyectmanager.auth.infrastructure.adapters.input.http;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.api.proyectmanager.auth.domain.ports.LoginUseCase;
import com.api.proyectmanager.auth.domain.ports.RegisterUseCase;
import com.api.proyectmanager.auth.infrastructure.adapters.input.dto.AuthResponse;
import com.api.proyectmanager.auth.infrastructure.adapters.input.dto.LoginRequest;
import com.api.proyectmanager.auth.infrastructure.adapters.input.dto.RegisterRequest;
import com.api.proyectmanager.shared.adapters.http.Response;

@Controller
@RequestMapping("/api/auth")
public class AuthController {
    private final LoginUseCase loginUseCase;
    private final RegisterUseCase registerUseCase;

    // Inyectamos el puerto de entrada, manteniendo el desacoplamiento técnico
    public AuthController(LoginUseCase loginUseCase, RegisterUseCase registerUseCase) {
        this.loginUseCase = loginUseCase;
        this.registerUseCase = registerUseCase;
    }

    @PostMapping("/login")
    public ResponseEntity<Response<AuthResponse>> login(@RequestBody LoginRequest request) {
        // Ejecutamos el caso de uso y obtenemos los datos del token
        AuthResponse data = loginUseCase.login(request);
        // Creamos la respuesta estandarizada con éxito, mensaje y datos
        Response<AuthResponse> response = new Response<>(true, "Inicio de sesión exitoso.", data);
        // Retornamos la respuesta con código HTTP 200 OK y el cuerpo de la respuesta
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<Response<Void>> register(@RequestBody RegisterRequest request) {
        // Ejecutamos el caso de uso de registro
        registerUseCase.register(request);
        // Creamos la respuesta estandarizada con éxito, mensaje y datos nulos
        Response<Void> response = new Response<>(true, "Registro exitoso.");
        // Retornamos la respuesta con código HTTP 201 Created y el cuerpo de la respuesta
        return ResponseEntity.status(201).body(response);
    }
}
