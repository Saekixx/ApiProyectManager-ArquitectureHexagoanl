package com.api.proyectmanager.project.infrastructure.adapters.input.http;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.proyectmanager.project.application.project.ChangeLeaderById;
import com.api.proyectmanager.project.application.project.FindAll;
import com.api.proyectmanager.project.application.project.FindAllActiveByUserId;
import com.api.proyectmanager.project.application.project.FindById;
import com.api.proyectmanager.project.application.project.FindByLeaderId;
import com.api.proyectmanager.project.application.project.FindByMemberId;
import com.api.proyectmanager.project.application.project.Save;
import com.api.proyectmanager.project.application.project.ToggleActiveById;
import com.api.proyectmanager.project.application.project.Update;
import com.api.proyectmanager.project.domain.Project;
import com.api.proyectmanager.shared.adapters.http.Response;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


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
    private final FindByLeaderId findByLeaderIdService;
    private final FindByMemberId findByMemberIdService;
    private final ChangeLeaderById changeLeaderByIdService;

    public ProjectController(Save saveService, Update updateService, FindAll findAllService, FindAllActiveByUserId findAllActiveService, FindById findByIdService, ToggleActiveById toggleActiveByIdService, FindByLeaderId findByLeaderIdService, FindByMemberId findByMemberIdService, ChangeLeaderById changeLeaderByIdService) {
        this.saveService = saveService;
        this.updateService = updateService;
        this.findAllService = findAllService;
        this.findAllActiveService = findAllActiveService;
        this.findByIdService = findByIdService;
        this.toggleActiveByIdService = toggleActiveByIdService;
        this.findByLeaderIdService = findByLeaderIdService;
        this.findByMemberIdService = findByMemberIdService;
        this.changeLeaderByIdService = changeLeaderByIdService;
    }


    // Endpoint para obtener todos los proyectos
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/")
    public ResponseEntity<Response<List<Project>>> findAll() {
        return ResponseEntity.ok(new Response<>(true, "Proyectos encontrados.", findAllService.findAll()));
    }

    // Endpoint para crear un nuevo proyecto
    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response<Void>> createProject(@RequestParam Integer projectId, @RequestParam Integer leaderId, @RequestParam List<Integer> memberIds) {
            saveService.execute(projectId, leaderId, memberIds);
            return ResponseEntity.ok(new Response<>(true, "Proyecto creado correctamente."));
    }

    // Endpoint para actualizar un proyecto existente
    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response<Void>> updateProject(@PathVariable Integer id, @RequestBody Project updatedProject) {
        updateService.execute(id, updatedProject);
        return ResponseEntity.ok(new Response<>(true, "Proyecto actualizado correctamente."));
    }

    // Enpoint para listar todos los proyectos activos por usuario
    @GetMapping("/active/{userId}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('COLABORADOR')")
    public ResponseEntity<Response<List<Project>>> findAllActive(@PathVariable Integer userId) {
        return ResponseEntity.ok(new Response<>(true, "Proyectos activos encontrados.", findAllActiveService.execute(userId)));
    }
    
    // Endpoint para obtener un proyecto por su ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response<Project>> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(new Response<>(true, "Proyecto encontrado.", findByIdService.execute(id)));
    }

    // Endpoint para cambiar el líder de un proyecto
    @PostMapping("/change-leader/{projectId}/{newLeaderId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response<Void>> changeLeader(@PathVariable Integer projectId, @PathVariable Integer newLeaderId) {
        changeLeaderByIdService.execute(projectId, newLeaderId);
        return ResponseEntity.ok(new Response<>(true, "Líder del proyecto cambiado correctamente."));
    }

    // Endpoint para activar o desactivar un proyecto por su ID
    @PostMapping("/toggle-active/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response<Void>> activateProjectById(@PathVariable Integer id) {
        String result = toggleActiveByIdService.execute(id);
        return ResponseEntity.ok(new Response<>(true, result));
    }

    // Endpoint para obtener proyectos por ID de líder
    @GetMapping("/leader/{leaderId}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('COLABORADOR')")
    public ResponseEntity<Response<List<Project>>> findByLeaderId(@PathVariable Integer leaderId) {
        return ResponseEntity.ok(new Response<>(true, "Proyectos encontrados por líder.", findByLeaderIdService.execute(leaderId)));
    }

    // Endpoint para obtener proyectos por ID de miembro
    @GetMapping("/member/{memberId}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('COLABORADOR')")
    public ResponseEntity<Response<List<Project>>> findByMemberId(@PathVariable Integer memberId) {
        return ResponseEntity.ok(new Response<>(true, "Proyectos encontrados por miembro.", findByMemberIdService.execute(memberId)));
    }
}
