package com.api.proyectmanager.project.domain.ports;

import java.util.List;
import java.util.Optional;

import com.api.proyectmanager.project.domain.ProjectMiembro;

public interface ProjectMiembroRepository {
    // Puerto para agregar un miembro a un proyecto
    ProjectMiembro addMemberToProject(ProjectMiembro projectMiembro);
    // Puerto para eliminar un miembro de un proyecto
    void removeMemberFromProject(ProjectMiembro projectMiembro);

    // Puerto para buscar si el usuario ya existe en el proyecto (y saber si está activo o no)
    Optional<ProjectMiembro> findByProjectIdAndUserId(Integer projectId, Integer userId);
    // Puerto para ver los miembros actuales del equipo
    List<ProjectMiembro> findActiveMembersByProjectId(Integer projectId);
    // Para ver los antiguos miembros (tu requerimiento del histórico)
    List<ProjectMiembro> findPastMembersByProjectId(Integer projectId);
}
