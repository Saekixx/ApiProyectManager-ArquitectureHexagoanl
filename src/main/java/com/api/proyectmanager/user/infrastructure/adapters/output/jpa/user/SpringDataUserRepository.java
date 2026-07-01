package com.api.proyectmanager.user.infrastructure.adapters.output.jpa.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface SpringDataUserRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByUsername(String username);
    @Modifying
    @Query("UPDATE UserEntity u SET u.isActive = :active WHERE u.id = :id")
    void updateStatus(@Param("id") Integer id, @Param("active") Boolean active);
}
