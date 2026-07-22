package com.api.proyectmanager.project.infrastructure.adapters.output.jpa.projectMiembro;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataProjectMiembroRepository extends JpaRepository<ProjectMiembroEntity, Integer> {
    // Método para buscar un miembro de proyecto por projectId y userId
    Optional<ProjectMiembroEntity> findByProjectIdAndUserId(Integer projectId, Integer userId);
    // Metodo para buscar todos los miembros de un proyecto por projectId
    @Query("""
        SELECT pm FROM ProjectMiembroEntity pm 
        JOIN FETCH pm.project p 
        JOIN FETCH pm.user u 
        LEFT JOIN FETCH u.rol 
        WHERE p.id = :projectId
    """)
    List<ProjectMiembroEntity> findAllMembersByProjectId(@Param("projectId") Integer projectId);
}
