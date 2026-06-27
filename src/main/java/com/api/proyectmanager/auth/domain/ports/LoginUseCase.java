package com.api.proyectmanager.auth.domain.ports;

import com.api.proyectmanager.auth.infrastructure.adapters.input.dto.AuthResponse;
import com.api.proyectmanager.auth.infrastructure.adapters.input.dto.LoginRequest;

public interface LoginUseCase {
    // Puerto para loguear al usuario
    AuthResponse login(LoginRequest request);
}
