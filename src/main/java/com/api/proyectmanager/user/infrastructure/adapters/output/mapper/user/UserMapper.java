package com.api.proyectmanager.user.infrastructure.adapters.output.mapper.user;

import com.api.proyectmanager.user.domain.User;
import com.api.proyectmanager.user.infrastructure.adapters.output.jpa.user.UserEntity;
import com.api.proyectmanager.user.infrastructure.adapters.output.mapper.rol.RolMapper;

// Clase para mapear entre User y UserEntity
public class UserMapper {
    // Método para mapear de UserEntity a User
    public static User toDomain(UserEntity entity) {

        if (entity == null) {
            return null;
        }

        return new User(
            entity.getId(),
            entity.getUsername(),
            entity.getPassword(),
            entity.getFullname(),
            entity.getEmail(),
            entity.getIsActive(),
            RolMapper.toDomain(entity.getRol()),
            entity.getCreatedAt(),
            entity.getUpdatedAt()
        );
    }

    // Método para mapear de User a UserEntity
    public static UserEntity toEntity(User user) {

        if (user == null) {
            return null;
        }

        UserEntity entity = new UserEntity();
        entity.setId(user.getId());
        entity.setUsername(user.getUsername());
        entity.setPassword(user.getPassword());
        entity.setFullname(user.getFullname());
        entity.setEmail(user.getEmail());
        entity.setIsActive(user.getIsActive());
        entity.setRol(RolMapper.toEntity(user.getRol()));
        entity.setCreatedAt(user.getCreatedAt());
        entity.setUpdatedAt(user.getUpdatedAt());

        return entity;
    }
}
