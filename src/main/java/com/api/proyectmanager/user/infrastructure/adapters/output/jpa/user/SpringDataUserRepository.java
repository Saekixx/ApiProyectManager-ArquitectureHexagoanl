package com.api.proyectmanager.user.infrastructure.adapters.output.jpa.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface SpringDataUserRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByUsername(String username);   

    // Metodo para obtener todos los usuarios por un conjunto de IDs
    @Query("SELECT u FROM UserEntity u WHERE u.id IN :ids")
    List<UserEntity> findAllByIds(Set<Integer> ids);

    // Metodo para actualizar el estado de un usuario (activo/inactivo)
    @Modifying
    @Query("UPDATE UserEntity u SET u.isActive = :active WHERE u.id = :id")
    void updateStatus(@Param("id") Integer id, @Param("active") Boolean active);
    // Metodo para verificar si un usuario está activo
    @Query("SELECT u.isActive FROM UserEntity u WHERE u.id = :id")
    Optional<Boolean> isUserActive(@Param("id") Integer id);
    // Metodo para verificar si un usuario es admin
    boolean existsByIdAndRol_Name(Integer userId, String roleName);
}
