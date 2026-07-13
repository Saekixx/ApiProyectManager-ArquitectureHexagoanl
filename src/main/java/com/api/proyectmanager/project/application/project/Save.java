package com.api.proyectmanager.project.application.project;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
        // Validar que el líder exista en la base de datos
        User leader = userRepository.findById(request.leaderId())
                        .orElseThrow(() -> new BusinessException("Usuario no encontrado con ID: " + request.leaderId()));
        // Crear una instancia de Project con los datos proporcionados
        Project project = new Project();
        project.setName(request.name());
        project.setDescription(request.description());
        project.setLeader(leader);
        project.setActive(true);
        // Guardamos el proyecto en la base de datos para generar su ID
        Project proyectoGuardado = projectRepository.save(project);
        // Crear una lista para acumular todos los miembros y guardarlos en un solo lote
        List<ProjectMiembro> miembrosAGuardar = new ArrayList<>();
        // Instanciar el registro del líder como miembro activo del proyecto
        ProjectMiembro liderMiembro = new ProjectMiembro();
        liderMiembro.setProject(proyectoGuardado);
        liderMiembro.setUser(leader);
        liderMiembro.setIsActive(true);
        miembrosAGuardar.add(liderMiembro);
        // 5. Validar y registrar a los miembros adicionales de manera optimizada
        if (request.memberIds() != null && !request.memberIds().isEmpty()) {

            // Filtramos al líder directamente del Set (que ya no tiene duplicados)
            Set<Integer> cleanMemberIds = request.memberIds().stream()
                    .filter(id -> !id.equals(request.leaderId()))
                    .collect(Collectors.toSet());

            if (!cleanMemberIds.isEmpty()) {
                // Obtenemos todos los miembros en una sola consulta
                List<User> membersFetched = userRepository.findAllByIds(cleanMemberIds);

                // Validar que se hayan encontrado todos los IDs solicitados
                if (membersFetched.size() != cleanMemberIds.size()) {
                    throw new BusinessException("Uno o más miembros especificados no existen en el sistema.");
                }

                // Instanciar los miembros adicionales y acumularlos en la lista
                for (User member : membersFetched) {
                    ProjectMiembro projectMember = new ProjectMiembro();
                    projectMember.setProject(proyectoGuardado);
                    projectMember.setUser(member);
                    projectMember.setIsActive(true);
                    
                    miembrosAGuardar.add(projectMember);
                }
            }
        }
        // Guardar todos los miembros de golpe
        projectMiembroRepository.saveAll(miembrosAGuardar);
    }
}
