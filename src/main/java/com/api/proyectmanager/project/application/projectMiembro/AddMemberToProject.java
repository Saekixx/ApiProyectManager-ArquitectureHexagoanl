package com.api.proyectmanager.project.application.projectMiembro;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.proyectmanager.project.domain.Project;
import com.api.proyectmanager.project.domain.ProjectMiembro;
import com.api.proyectmanager.project.domain.ports.ProjectMemberRepository;
import com.api.proyectmanager.project.domain.ports.ProjectRepository;
import com.api.proyectmanager.shared.domain.BusinessException;
import com.api.proyectmanager.user.domain.User;
import com.api.proyectmanager.user.domain.ports.UserRepository;

@Service
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

    @Transactional
    public String execute(Integer projectId, Integer userId) {
        // Validar que el proyecto exista
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new BusinessException("El proyecto con ID " + projectId + " no existe."));
        // Validar que el usuario exista
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException("El usuario con ID " + userId + " no existe."));
        // Validar que el usuario este activo en el sistema antes de agregarlo al proyecto
        if (!user.getIsActive()) {
            throw new BusinessException("No se puede agregar al proyecto porque el usuario está desactivado en el sistema.");
        }
        // Buscar si ya existe una relación previa (activa o inactiva)
        Optional<ProjectMiembro> miembroExistente = 
            projectMemberRepository.findByProjectIdAndUserId(projectId, userId);
        if (miembroExistente.isPresent()) {
            ProjectMiembro miembro = miembroExistente.get(); // Obtenemos el miembro existente para verificar su estado
            // CASO A: Ya está activo trabajando en este proyecto actualmente
            if (miembro.getIsActive()) {
                throw new BusinessException("El usuario con ID " + userId + " ya es un miembro ACTIVO de este proyecto.");
            }
            // CASO B: Si el usuario a existe en el proyecto pero está inactivo, lo reactivamos
            miembro.setIsActive(true);
            miembro.setJoinedAt(LocalDateTime.now()); // Guardamos la fecha de reactivación del proyecto
            miembro.setExitedAt(null); // Limpiamos la fecha de salida ya que ahora está activo nuevamente
            projectMemberRepository.save(miembro); // Guardamos la reactivación del miembro en el proyecto
            // Retornamos un mensaje indicando que el usuario ha sido reactivado exitosamente en el proyecto
            return "El usuario con ID " + userId + " ha sido reactivado exitosamente en el proyecto con ID " + projectId + ".";
        }
        // Crear un objeto ProjectMiembro para representar la relación entre el proyecto y el usuario
        ProjectMiembro projectMiembro = new ProjectMiembro();
        projectMiembro.setProject(project); // Asignar el proyecto al miembro
        projectMiembro.setUser(user); // Asignar el usuario al miembro
        projectMiembro.setIsActive(true); // Establecer el estado como activo
        projectMiembro.setJoinedAt(LocalDateTime.now()); // Guardar la fecha de unión
        projectMiembro.setExitedAt(null); // Limpiar la fecha de salida
        // Guardar la relación en el repositorio de miembros del proyecto
        projectMemberRepository.save(projectMiembro);
        // Retornar un mensaje indicando que el miembro fue agregado exitosamente
        return "El usuario con ID " + userId + " ha sido agregado exitosamente al proyecto con ID " + projectId + ".";
    }
}
