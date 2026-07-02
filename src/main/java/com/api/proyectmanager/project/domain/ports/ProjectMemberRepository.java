package com.api.proyectmanager.project.domain.ports;

import java.util.List;
import java.util.Optional;

import com.api.proyectmanager.project.domain.ProjectMiembro;

public interface ProjectMemberRepository {
    // Puerto para guardar la asignación, la actualizar o la desactivar (Histórico)
    ProjectMiembro save(ProjectMiembro projectMiembro);

    // Puerto para buscar la asignación única de un usuario en un proyecto (Saber si existe o su estado)
    Optional<ProjectMiembro> findByProjectIdAndUserId(Integer projectId, Integer userId);

    // Puerto para obtener todos los miembros activos de un proyecto (Tablero diario)
    List<ProjectMiembro> findActiveMembersByProjectId(Integer projectId);
    // Puerto para obtener todos los miembros (activos e inactivos) de un proyecto (Historial completo)
    List<ProjectMiembro> findAllMembersByProjectId(Integer projectId);  
}
