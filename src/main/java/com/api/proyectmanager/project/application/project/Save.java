package com.api.proyectmanager.project.application.project;

import java.util.List;
import java.util.Set;

import org.springframework.transaction.annotation.Transactional;

import com.api.proyectmanager.project.application.dtos.ProjectRequest;
import com.api.proyectmanager.project.domain.Project;
import com.api.proyectmanager.project.domain.ProjectMiembro;
import com.api.proyectmanager.project.domain.ports.ProjectMemberRepository;
import com.api.proyectmanager.project.domain.ports.ProjectRepository;
import com.api.proyectmanager.shared.domain.BusinessException;
import com.api.proyectmanager.shared.domain.annotation.UseCase;
import com.api.proyectmanager.user.domain.User;
import com.api.proyectmanager.user.domain.ports.UserRepository;

@UseCase
public class Save {
    private final ProjectRepository projectRepository; // Repositorio de proyectos (PORTS)
    private final UserRepository userRepository; // Repositorio de usuarios (PORTS)
    private final ProjectMemberRepository projectMiembroRepository; // Repositorio de miembros de proyectos (PORTS)

    // Constructor para inyectar el repositorio de proyectos
    public Save(ProjectRepository projectRepository, UserRepository userRepository, ProjectMemberRepository projectMiembroRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.projectMiembroRepository = projectMiembroRepository;
    }

    @Transactional
    public void execute(ProjectRequest request) {
        // Validar que el proyecto exista en la base de datos
        Project project = projectRepository.findById(request.projectId())
                        .orElseThrow(() -> new IllegalArgumentException("Proyecto no encontrado con ID: " + request.projectId()));
        // Validar que el líder exista en la base de datos
        User leader = userRepository.findById(request.leaderId())
                        .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con ID: " + request.leaderId()));
        // Asignar el líder al proyecto 
        project.setLeader(leader);
        // Guardar el proyecto en la base de datos
        Project proyectoGuardado = projectRepository.save(project);
        // Registrar automáticamente al Líder como miembro activo del proyecto
        ProjectMiembro projectMiembro = new ProjectMiembro();
        projectMiembro.setProject(proyectoGuardado); // Asociar el proyecto guardado al miembro
        projectMiembro.setUser(leader); // Asociar el líder como miembro del proyecto
        projectMiembro.setIsActive(true); // Marcar al líder como miembro activo
        projectMiembroRepository.save(projectMiembro); // Guardar el miembro en la base de datos
        // Validar y registrar a los miembros proporcionados en la lista de IDs
        Set<Integer> memberIds = request.memberIds();
        if (memberIds != null && !memberIds.isEmpty()) {
            for (Integer memberId : memberIds) {
                // Evitar agregar al líder como miembro nuevamente
                if (memberId.equals(request.leaderId())) continue; 
                // Validar que el miembro exista en la base de datos
                User member = userRepository.findById(memberId)
                        .orElseThrow(() -> new BusinessException("Usuario no encontrado con ID: " + memberId));   
                // Crear y guardar un nuevo ProjectMiembro para cada miembro
                ProjectMiembro projectMember = new ProjectMiembro(); // Crear una nueva instancia de ProjectMiembro para cada miembro
                projectMember.setProject(proyectoGuardado); // Asociar el proyecto guardado al miembro
                projectMember.setUser(member); // Asociar el miembro al proyecto
                projectMember.setIsActive(true); // Marcar al miembro como activo
                projectMiembroRepository.save(projectMember); // Guardar el miembro en la base de datos
            }
        }  
    }
}
