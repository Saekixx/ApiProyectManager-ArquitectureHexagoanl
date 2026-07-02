package com.api.proyectmanager.project.application.project;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.proyectmanager.project.domain.Project;
import com.api.proyectmanager.project.domain.ProjectMiembro;
import com.api.proyectmanager.project.domain.ports.ProjectMemberRepository;
import com.api.proyectmanager.project.domain.ports.ProjectRepository;
import com.api.proyectmanager.shared.domain.BusinessException;
import com.api.proyectmanager.user.domain.User;
import com.api.proyectmanager.user.domain.ports.UserRepository;

@Service("projectSave")
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
    public void execute(Integer projectId, Integer leaderId, List<Integer> memberIds) {
        // Validar que el proyecto exista en la base de datos
        Project project = projectRepository.findById(projectId)
                        .orElseThrow(() -> new IllegalArgumentException("Proyecto no encontrado con ID: " + projectId));
        // Validar que el líder exista en la base de datos
        User leader = userRepository.findById(leaderId)
                        .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con ID: " + leaderId));
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
        if (memberIds != null && !memberIds.isEmpty()) {
            for (Integer memberId : memberIds) {
                // Evitar agregar al líder como miembro nuevamente
                if (memberId.equals(leaderId)) continue; 
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
