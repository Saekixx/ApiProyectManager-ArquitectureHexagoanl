package com.api.proyectmanager.project.application.project;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
    public void execute(Integer leaderId, List<Integer> memberIds, String name, String description) {
        // Validar que el líder exista en la base de datos
        User leader = userRepository.findById(leaderId)
                        .orElseThrow(() -> new BusinessException("Usuario no encontrado con ID: " + leaderId));

        // Crear y guardar el nuevo proyecto
        Project project = new Project();
        project.setName(name); // Establecer el nombre del proyecto
        project.setDescription(description); // Establecer la descripción del proyecto
        project.setLeader(leader); // Asociar el líder al proyecto
        project.setActive(true); // Marcar el proyecto como activo
        Project proyectoGuardado = projectRepository.save(project);

        // Crear y guardar el ProjectMiembro para el líder del proyecto
        ProjectMiembro projectMiembro = new ProjectMiembro();
        projectMiembro.setProject(proyectoGuardado); // Asociar el proyecto guardado al miembro
        projectMiembro.setUser(leader); // Asociar el líder como miembro del proyecto
        projectMiembro.setIsActive(true); // Marcar al líder como miembro activo
        projectMiembroRepository.save(projectMiembro); // Guardar el miembro en la base de datos

        // Validar y registrar a los miembros optimizadamente
        if (memberIds != null && !memberIds.isEmpty()) {

            // Filtrar los IDs de miembros para eliminar duplicados y excluir al líder
            Set<Integer> uniqueMemberIds = memberIds.stream()
                .filter(id -> !id.equals(leaderId))
                .collect(Collectors.toSet());

            if (!uniqueMemberIds.isEmpty()) {
                // Obtener todos los miembros de la base de datos en una sola consulta
                List<User> membersFetched = userRepository.findAllByIds(uniqueMemberIds);

                // Validar que se hayan encontrado todos los IDs solicitados
                if (membersFetched.size() != uniqueMemberIds.size()) {
                    throw new BusinessException("Uno o más miembros especificados no existen en el sistema.");
                }

                // Registrar a cada miembro en el proyecto
                for (User member : membersFetched) {
                    ProjectMiembro projectMember = new ProjectMiembro();
                    projectMember.setProject(proyectoGuardado);
                    projectMember.setUser(member);
                    projectMember.setIsActive(true);
                    
                    projectMiembroRepository.save(projectMember);
                }
            }
        }  
    }
}
