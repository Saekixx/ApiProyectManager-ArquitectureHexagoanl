package com.api.proyectmanager.project.application.project;

import org.springframework.stereotype.Service;

import com.api.proyectmanager.project.domain.ports.ProjectRepository;
import com.api.proyectmanager.user.domain.User;
import com.api.proyectmanager.user.domain.ports.UserRepository;

@Service
public class ChangeLeader {
    private final ProjectRepository projectRepository; // Repositorio de proyectos (PORTS)
    private final UserRepository userRepository;    // Repositorio de usuarios (PORTS)

    // Constructor para inyectar los repositorios de proyectos y usuarios
    public ChangeLeader(ProjectRepository projectRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    public void execute(Integer projectId, Integer newLeaderId) {
        // Validar que el proyecto exista en la base de datos
        projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("El proyecto con ID " + projectId + " no existe."));
        // Validar que el nuevo líder exista en la base de datos y este activo
        User newLeader = userRepository.findById(newLeaderId)
                .filter(user -> user.getIsActive()) // Asegurarse de que el usuario esté activo
                .orElseThrow(() -> new RuntimeException("El usuario con ID " + newLeaderId + " no existe o no está activo."));
        // Cambiar el líder del proyecto en el repositorio usando el puerto
        projectRepository.changeLeader(projectId, newLeader);
    }
}
