package com.api.proyectmanager.auth.adapters.http;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.api.proyectmanager.auth.adapters.dto.AuthResponse;
import com.api.proyectmanager.auth.adapters.dto.LoginRequest;
import com.api.proyectmanager.auth.ports.LoginUseCase;
import com.api.shared.adapters.http.ApiResponse;

@Controller
@RequestMapping("/api/auth")
public class AuthController {
    private final LoginUseCase loginUseCase;

    // Inyectamos el puerto de entrada, manteniendo el desacoplamiento técnico
    public AuthController(LoginUseCase loginUseCase) {
        this.loginUseCase = loginUseCase;
    }

    @PostMapping("/login")
    public ApiResponse<AuthResponse> login(@RequestBody LoginRequest request) {
        // Ejecutamos el caso de uso y obtenemos los datos del token
        AuthResponse data = loginUseCase.login(request);
        // Retornamos una respuesta con el estado de éxito, un mensaje y los datos
        return new ApiResponse<>(true, "Login exitoso", data);
    }
}
