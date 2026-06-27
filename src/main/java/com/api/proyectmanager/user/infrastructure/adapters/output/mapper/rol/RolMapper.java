package com.api.proyectmanager.user.infrastructure.adapters.output.mapper.rol;

import com.api.proyectmanager.user.domain.Rol;
import com.api.proyectmanager.user.infrastructure.adapters.output.jpa.rol.RolEntity;

// Clase para mapear los roles de la base de datos a objetos de dominio y viceversa
public class RolMapper {
    // Metodo para mapear de RolEntity a Rol
    public static Rol toDomain(RolEntity entity) {
        if (entity == null) {
            return null;
        }
        return new Rol(entity.getId(), entity.getName(), entity.getDescription());
    }

    // Metodo para mapear de Rol a RolEntity
    public static RolEntity toEntity(Rol rol) {
        if (rol == null) {
            return null;
        }
        RolEntity entity = new RolEntity();
        entity.setId(rol.getId());
        entity.setName(rol.getName());
        entity.setDescription(rol.getDescription());
        return entity;
    }
}
