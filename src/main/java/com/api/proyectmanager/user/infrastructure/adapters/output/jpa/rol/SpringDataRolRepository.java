package com.api.proyectmanager.user.infrastructure.adapters.output.jpa.rol;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataRolRepository extends JpaRepository<RolEntity, Integer> {
    Optional<RolEntity> findById(Integer id);
    Optional<RolEntity> findByName(String name);
}
