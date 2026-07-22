package com.api.proyectmanager.project.infrastructure.adapters.input.http;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.proyectmanager.project.application.dtos.ProjectRequest;
import com.api.proyectmanager.project.application.dtos.ProjectUpdate;
import com.api.proyectmanager.project.application.project.ChangeLeaderById;
import com.api.proyectmanager.project.application.project.FindAll;
import com.api.proyectmanager.project.application.project.FindAllActiveByUserId;
import com.api.proyectmanager.project.application.project.FindById;
import com.api.proyectmanager.project.application.project.FindByMemberId;
import com.api.proyectmanager.project.application.project.Save;
import com.api.proyectmanager.project.application.project.ToggleActiveById;
import com.api.proyectmanager.project.application.project.Update;
import com.api.proyectmanager.project.domain.Project;
import com.api.proyectmanager.project.infrastructure.adapters.input.dtos.request.ProjectLeader;
import com.api.proyectmanager.project.infrastructure.adapters.input.dtos.response.ProjectListDTO;
import com.api.proyectmanager.shared.adapters.http.Response;
import com.api.proyectmanager.user.application.user.IdFindByEmail;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;

@RestController
@RequestMapping("/api/projects")
@CrossOrigin(origins = "*")
public class ProjectController {
    private final Save saveService;
    private final Update updateService;
    private final FindAll findAllService;
    private final FindAllActiveByUserId findAllActiveService;
    private final FindById findByIdService;
    private final ToggleActiveById toggleActiveByIdService;
    private final FindByMemberId findByMemberIdService;
    private final ChangeLeaderById changeLeaderByIdService;
    private final IdFindByEmail idFindByEmailService; // Servicio para encontrar ID de usuario por email

    public ProjectController(Save saveService, Update updateService, FindAll findAllService, FindAllActiveByUserId findAllActiveService, FindById findByIdService, ToggleActiveById toggleActiveByIdService, FindByMemberId findByMemberIdService, ChangeLeaderById changeLeaderByIdService, IdFindByEmail idFindByEmailService) {
        this.saveService = saveService;
        this.updateService = updateService;
        this.findAllService = findAllService;
        this.findAllActiveService = findAllActiveService;
        this.findByIdService = findByIdService;
        this.toggleActiveByIdService = toggleActiveByIdService;
        this.findByMemberIdService = findByMemberIdService;
        this.changeLeaderByIdService = changeLeaderByIdService;
        this.idFindByEmailService = idFindByEmailService;
    }


    // Endpoint para obtener todos los proyectos
    // /api/projects/
    @GetMapping("/")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('COLABORADOR')")
    public ResponseEntity<Response<List<ProjectListDTO>>> findAll(Authentication authentication) {
        // Detectar el rol del usuario 
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ADMIN"));
        // Obtener el Email del usuario
        String userEmail = authentication.getName();
        // Obtener el ID del usuario a partir del Email
        Integer userId = idFindByEmailService.execute(userEmail);
        // Creamos una lista de proyectos vacía
        List<Project> projects;
        // Si el usuario es admin, obtenemos todos los proyectos
        if (isAdmin) projects = findAllService.findAll();
        // Si no, obtenemos solo los proyectos activos del usuario
        else projects = findAllActiveService.execute(userId);
        // Convertir la lista de proyectos a una lista de ProjectListDTO
        List<ProjectListDTO> projectDTOs = projects.stream()
                .map(ProjectListDTO::fromDomain)
                .toList();
        // Devolver la respuesta con los proyectos encontrados
        return ResponseEntity.ok(new Response<>(true, "Proyectos encontrados.", projectDTOs));
    }

    // Endpoint para crear un nuevo proyecto
    @PostMapping("/")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Transactional
    public ResponseEntity<Response<Void>> createProject(@RequestBody ProjectRequest request) {
            saveService.execute(request);
            return ResponseEntity.ok(new Response<>(true, "Proyecto creado correctamente."));
    }

    // Endpoint para actualizar un proyecto existente
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response<Void>> updateProject(@PathVariable Integer id, @RequestBody ProjectUpdate updatedProject) {
        updateService.execute(id, updatedProject);
        return ResponseEntity.ok(new Response<>(true, "Proyecto actualizado correctamente."));
    }
    
    // Endpoint para obtener un proyecto por su ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response<Project>> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(new Response<>(true, "Proyecto encontrado.", findByIdService.execute(id)));
    }

    // Endpoint para cambiar el líder de un proyecto
    @PostMapping("/change-leader")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response<Void>> changeLeader(@RequestBody ProjectLeader projectLeader) {
        changeLeaderByIdService.execute(projectLeader.projectId(), projectLeader.newLeaderId());
        return ResponseEntity.ok(new Response<>(true, "Líder del proyecto cambiado correctamente."));
    }

    // Endpoint para activar o desactivar un proyecto por su ID
    @PostMapping("/toggle-active/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response<Void>> activateProjectById(@PathVariable Integer id) {
        String result = toggleActiveByIdService.execute(id);
        return ResponseEntity.ok(new Response<>(true, result));
    }

    // Endpoint para obtener proyectos por ID de miembro
    @GetMapping("/member/{memberId}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('COLABORADOR')")
    public ResponseEntity<Response<List<ProjectListDTO>>> findByMemberId(@PathVariable Integer memberId) {
        // Obtener los proyectos asociados al ID de miembro
        List<Project> projects = findByMemberIdService.execute(memberId);
        // Convertir la lista de proyectos a una lista de ProjectListDTO
        List<ProjectListDTO> projectDTOs = projects.stream()
                .map(ProjectListDTO::fromDomain)
                .toList();
        // Devolver la respuesta con los proyectos encontrados
        return ResponseEntity.ok(new Response<>(true, "Proyectos encontrados por miembro.", projectDTOs));
    }
}
