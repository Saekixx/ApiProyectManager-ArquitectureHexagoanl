package com.api.proyectmanager.user.application.dtos;

public record ChangeRolRequest(
    Integer rolId
) {
    public ChangeRolRequest {
        if (rolId == null) {
            throw new IllegalArgumentException("El Id del rol es obligatorio.");
        }
    }
}
