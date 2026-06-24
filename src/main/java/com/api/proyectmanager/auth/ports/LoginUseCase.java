package com.api.proyectmanager.auth.ports;

import com.api.proyectmanager.auth.adapters.dto.AuthResponse;
import com.api.proyectmanager.auth.adapters.dto.LoginRequest;

public interface LoginUseCase {
    // Puerto para loguear al usuario
    AuthResponse login(LoginRequest request);
}
