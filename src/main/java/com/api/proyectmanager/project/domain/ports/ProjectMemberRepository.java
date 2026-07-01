package com.api.proyectmanager.project.domain.ports;

import java.util.List;
import java.util.Optional;

import com.api.proyectmanager.project.domain.ProjectMiembro;

public interface ProjectMemberRepository {
    // Puerto para agregar un miembro a un proyecto
    Boolean addMemberToProject(ProjectMiembro projectMiembro);
    // Puerto para eliminar un miembro de un proyecto
    void removeMemberFromProject(ProjectMiembro projectMiembro);

    // Puerto para buscar un miembro por su ID de proyecto y ID de usuario
    ProjectMiembro findById(Integer projectId, Integer userId);
    // Puerto para buscar si el usuario ya existe en el proyecto (y saber si está activo o no)
    Optional<ProjectMiembro> findByProjectIdAndUserId(Integer projectId, Integer userId);
    // Puerto para ver todos los miembros de un proyecto
    List<ProjectMiembro> findAllMembersByProjectId(Integer projectId);
    // Puerto para ver los miembros actuales del equipo
    List<ProjectMiembro> findActiveMembersByProjectId(Integer projectId);
    // Para ver los antiguos miembros (tu requerimiento del histórico)
    List<ProjectMiembro> findPastMembersByProjectId(Integer projectId);
}
