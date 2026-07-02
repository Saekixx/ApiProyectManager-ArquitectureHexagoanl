package com.api.proyectmanager.project.infrastructure.adapters.output.jpa.project;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataProjectRepository extends JpaRepository<ProjectEntity, Integer> {
    // Puerto para listar todos los proyectos por líder
    List<ProjectEntity> findByLeaderId(Integer leaderId);
    // Puerto para listar todos los proyectos por miembro
    @Query("SELECT p FROM ProjectEntity p JOIN p.projectMembers pm WHERE pm.user.id = :memberId")
    List<ProjectEntity> findByMemberId(Integer memberId);
}
