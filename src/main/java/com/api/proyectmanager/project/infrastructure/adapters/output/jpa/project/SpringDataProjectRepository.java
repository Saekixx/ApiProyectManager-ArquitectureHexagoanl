package com.api.proyectmanager.project.infrastructure.adapters.output.jpa.project;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataProjectRepository extends JpaRepository<ProjectEntity, Integer> {
    // Puerto para listar todos los proyectos activos
    @Query("SELECT DISTINCT p FROM ProjectEntity p JOIN p.projectMembers pm WHERE p.isActive = true AND pm.user.id = :userId")
    List<ProjectEntity> findAllActive(Integer userId);

    // Puerto para listar todos los proyectos activos por líder 
    @Query("SELECT p FROM ProjectEntity p WHERE p.leader.id = :leaderId AND p.isActive = true")
    List<ProjectEntity> findByLeaderId(Integer leaderId);

    // Puerto para listar todos los proyectos activos por miembro
    @Query("SELECT p FROM ProjectEntity p JOIN p.projectMembers pm WHERE pm.user.id = :memberId AND p.isActive = true")
    List<ProjectEntity> findByMemberId(Integer memberId);

    // Puerto para verificar si un usuario es miembro de un proyecto específico 
    Boolean existsByIdAndProjectMembers_IdAndIsActiveTrue(Integer projectId, Integer userId);

    // Puerto para verificar si un usuario es el líder de un proyecto específico
    Boolean existsByIdAndLeader_IdAndIsActiveTrue(Integer projectId, Integer userId);
}
