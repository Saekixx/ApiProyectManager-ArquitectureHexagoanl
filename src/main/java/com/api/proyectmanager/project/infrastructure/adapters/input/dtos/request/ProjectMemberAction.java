package com.api.proyectmanager.project.infrastructure.adapters.input.dtos.request;

public record ProjectMemberAction(
    Integer projectId, 
    Integer userId
) {
    
}
