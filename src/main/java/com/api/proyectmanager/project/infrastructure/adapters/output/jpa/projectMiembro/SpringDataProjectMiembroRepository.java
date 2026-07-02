package com.api.proyectmanager.project.infrastructure.adapters.output.jpa.projectMiembro;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataProjectMiembroRepository extends JpaRepository<ProjectMiembroEntity, Integer> {
    
}
