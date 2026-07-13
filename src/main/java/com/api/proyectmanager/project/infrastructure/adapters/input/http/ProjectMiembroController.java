package com.api.proyectmanager.project.infrastructure.adapters.input.http;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.proyectmanager.project.application.projectMiembro.AddMemberToProject;
import com.api.proyectmanager.project.application.projectMiembro.FindActiveMembersByProjectId;
import com.api.proyectmanager.project.application.projectMiembro.RemoveMemberToProject;
import com.api.proyectmanager.project.domain.ProjectMiembro;
import com.api.proyectmanager.project.infrastructure.adapters.input.dtos.request.ProjectMemberAction;
import com.api.proyectmanager.shared.adapters.http.Response;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/projects/members")
@CrossOrigin(origins = "*")
public class ProjectMiembroController {
    private final AddMemberToProject addMemberToProjectService;
    private final RemoveMemberToProject removeMemberFromProjectService;
    private final FindActiveMembersByProjectId findActiveMembersByProjectIdService;

    public ProjectMiembroController(AddMemberToProject addMemberToProjectService, RemoveMemberToProject removeMemberFromProjectService, FindActiveMembersByProjectId findActiveMembersByProjectIdService) {
        this.addMemberToProjectService = addMemberToProjectService;
        this.removeMemberFromProjectService = removeMemberFromProjectService;
        this.findActiveMembersByProjectIdService = findActiveMembersByProjectIdService;
    }

    // Endpoint para agregar un miembro a un proyecto
    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('COLABORADOR')")
    public ResponseEntity<Response<String>> addMember(@RequestBody ProjectMemberAction action) {
        String message = addMemberToProjectService.execute(action.projectId(), action.userId());
        return ResponseEntity.ok(new Response<>(true, message));
    }
    
    // Endpoint para eliminar un miembro de un proyecto
    @PostMapping("/remove")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('COLABORADOR')")
    public ResponseEntity<Response<String>> removeMember(@RequestBody ProjectMemberAction action) {
        String message = removeMemberFromProjectService.execute(action.projectId(), action.userId());
        return ResponseEntity.ok(new Response<>(true, message));
    }

    // Endpoint para obtener todos los miembros activos de un proyecto por su ID
    @GetMapping("/active")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('COLABORADOR')")
    public ResponseEntity<Response<List<ProjectMiembro>>> findActiveMembers(@RequestBody Integer projectId) {
        List<ProjectMiembro> members = findActiveMembersByProjectIdService.execute(projectId);
        return ResponseEntity.ok(new Response<>(true, "Miembros activos del proyecto encontrados.", members));
    }
    
}
