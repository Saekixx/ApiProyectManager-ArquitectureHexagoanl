package com.api.proyectmanager.project.infrastructure.adapters.output.jpa.projectMiembro;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataProjectMiembroRepository extends JpaRepository<ProjectMiembroEntity, Integer> {
    // Método para buscar un miembro de proyecto por projectId y userId
    Optional<ProjectMiembroEntity> findByProjectIdAndUserId(Integer projectId, Integer userId);
}
