package com.api.proyectmanager.project.infrastructure.adapters.input.http;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.proyectmanager.project.application.projectMiembro.AddMemberToProject;
import com.api.proyectmanager.project.application.projectMiembro.FindActiveMembersByProjectId;
import com.api.proyectmanager.project.application.projectMiembro.FindAllMembersByProjectId;
import com.api.proyectmanager.project.application.projectMiembro.RemoveMemberToProject;
import com.api.proyectmanager.project.domain.ProjectMiembro;
import com.api.proyectmanager.shared.adapters.http.Response;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/api/project-members")
@CrossOrigin(origins = "*")
public class ProjectMiembroController {
    private final AddMemberToProject addMemberToProjectService;
    private final RemoveMemberToProject removeMemberFromProjectService;
    private final FindAllMembersByProjectId findAllMembersByProjectIdService;
    private final FindActiveMembersByProjectId findActiveMembersByProjectIdService;

    public ProjectMiembroController(AddMemberToProject addMemberToProjectService, RemoveMemberToProject removeMemberFromProjectService, FindAllMembersByProjectId findAllMembersByProjectIdService, FindActiveMembersByProjectId findActiveMembersByProjectIdService) {
        this.addMemberToProjectService = addMemberToProjectService;
        this.removeMemberFromProjectService = removeMemberFromProjectService;
        this.findAllMembersByProjectIdService = findAllMembersByProjectIdService;
        this.findActiveMembersByProjectIdService = findActiveMembersByProjectIdService;
    }

    // Endpoint para agregar un miembro a un proyecto
    @PostMapping("/add-member/{projectId}/{userId}")
    public ResponseEntity<Response<String>> addMember(@PathVariable Integer projectId, @PathVariable Integer userId) {
        String message = addMemberToProjectService.execute(projectId, userId);
        return ResponseEntity.ok(new Response<>(true, message));
    }
    
    // Endpoint para eliminar un miembro de un proyecto
    @PostMapping("/remove-member/{projectId}/{userId}")
    public ResponseEntity<Response<String>> removeMember(@PathVariable Integer projectId, @PathVariable Integer userId) {
        String message = removeMemberFromProjectService.execute(projectId, userId);
        return ResponseEntity.ok(new Response<>(true, message));
    }

    // Endpoint para obtener todos los miembros de un proyecto por su ID 
    @GetMapping("/{projectId}")
    public ResponseEntity<Response<List<ProjectMiembro>>> findAllMembers(@PathVariable Integer projectId) {
        List<ProjectMiembro> members = findAllMembersByProjectIdService.execute(projectId);
        return ResponseEntity.ok(new Response<>(true, "Miembros del proyecto encontrados.", members));
    }

    // Endpoint para obtener todos los miembros activos de un proyecto por su ID
    @GetMapping("/active/{projectId}")
    public ResponseEntity<Response<List<ProjectMiembro>>> findActiveMembers(@PathVariable Integer projectId) {
        List<ProjectMiembro> members = findActiveMembersByProjectIdService.execute(projectId);
        return ResponseEntity.ok(new Response<>(true, "Miembros activos del proyecto encontrados.", members));
    }
    
}
