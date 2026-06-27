package com.api.proyectmanager.auth.domain.ports;

import com.api.proyectmanager.auth.infrastructure.adapters.input.dto.RegisterRequest;

public interface RegisterUseCase {
    // Puerto para registrar al usuario
    void register(RegisterRequest request);
}
