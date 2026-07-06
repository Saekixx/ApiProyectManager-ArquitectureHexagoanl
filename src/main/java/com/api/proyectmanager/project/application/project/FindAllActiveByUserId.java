package com.api.proyectmanager.project.application.project;

import java.util.List;

import org.springframework.stereotype.Service;

import com.api.proyectmanager.project.domain.Project;
import com.api.proyectmanager.project.domain.ports.ProjectRepository;
import com.api.proyectmanager.user.domain.ports.UserRepository;

@Service("projectFindAllActiveByUserId")
public class FindAllActiveByUserId {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    
    public FindAllActiveByUserId(ProjectRepository projectRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    public List<Project> execute(Integer userId) {
        // Verificamos si el usuario existe en la base de datos
        userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        // Retornamos la lista de proyectos activos asociados al usuario
        return projectRepository.findAllActiveByUserId(userId);
    }
}
