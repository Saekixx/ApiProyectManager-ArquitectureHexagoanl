package com.api.proyectmanager.project.application.projectMiembro;

import java.time.LocalDateTime;
import java.util.Optional;

import com.api.proyectmanager.project.domain.Project;
import com.api.proyectmanager.project.domain.ProjectMiembro;
import com.api.proyectmanager.project.domain.ports.ProjectMemberRepository;
import com.api.proyectmanager.project.domain.ports.ProjectRepository;
import com.api.proyectmanager.shared.domain.BusinessException;
import com.api.proyectmanager.shared.domain.annotation.UseCase;
import com.api.proyectmanager.user.domain.User;
import com.api.proyectmanager.user.domain.ports.UserRepository;

@UseCase
public class AddMemberToProject {
    private final ProjectMemberRepository projectMemberRepository; // Repositorio para manejar la persistencia de miembros del proyecto (PORTS)
    private final ProjectRepository projectRepository; // Repositorio para manejar la persistencia de proyectos (PORTS)
    private final UserRepository userRepository; // Repositorio para manejar la persistencia de usuarios (PORTS)

    // Constructor que inyecta el repositorio de miembros del proyecto
    public AddMemberToProject(ProjectMemberRepository projectMemberRepository, ProjectRepository projectRepository, UserRepository userRepository) {
        this.projectMemberRepository = projectMemberRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    public String execute(Integer projectId, Integer userId) {
        // Validamos que el usuario exista
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException("El usuario con ID " + userId + " no existe."));
        // Validamos que el usuario esté activo
        if (!user.getIsActive()) {
            throw new BusinessException("El usuario con ID " + userId + " no está activo.");
        }
        // Validamos que el proyecto exista
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new BusinessException("El proyecto con ID " + projectId + " no existe."));
        // Validamos que el proyecto esté activo
        if (!project.isActive()) {
            throw new BusinessException("El proyecto con ID " + projectId + " no está activo.");
        }
        // Traemos la lista de miembros del proyecto
        Optional<ProjectMiembro> miembroExistente = projectMemberRepository.findByProjectIdAndUserId(projectId, userId);
        // Si el miembro ya existe
        if (miembroExistente.isPresent()) {
            ProjectMiembro miembro = miembroExistente.get();
            // El miembro ya es activo
            if (miembro.getIsActive()) {
                throw new BusinessException("El usuario con ID " + userId + " ya es un miembro ACTIVO de este proyecto.");
            }
            // El miembro estaba inactivo, lo reactivamos
            miembro.setIsActive(true);
            miembro.setExitedAt(null); // Limpiamos la fecha de salida
            miembro.setJoinedAt(LocalDateTime.now()); // Actualizamos la fecha de ingreso
            // Guardamos el cambio en la base de datos a través del puerto
            projectMemberRepository.save(miembro);
            // Retornamos un mensaje indicando que el usuario ha sido reactivado exitosamente en el proyecto
            return "El usuario con ID " + userId + " ha sido reactivado exitosamente en el proyecto con ID " + projectId + ".";
        }
        // Si no existe, agregamos el miembro al proyecto
        ProjectMiembro nuevoMiembro = new ProjectMiembro();
        nuevoMiembro.setProject(project); // Le pasas el objeto de dominio puro
        nuevoMiembro.setUser(user);       // Le pasas el objeto de dominio puro
        nuevoMiembro.setIsActive(true);
        nuevoMiembro.setJoinedAt(LocalDateTime.now());
        nuevoMiembro.setExitedAt(null);
        // Guardamos el nuevo miembro en la base de datos a través del puerto
        projectMemberRepository.save(nuevoMiembro);
        // Retornamos un mensaje indicando que el usuario ha sido agregado exitosamente al proyecto
        return "El usuario con ID " + userId + " ha sido agregado exitosamente al proyecto con ID " + projectId + ".";
    }
}
