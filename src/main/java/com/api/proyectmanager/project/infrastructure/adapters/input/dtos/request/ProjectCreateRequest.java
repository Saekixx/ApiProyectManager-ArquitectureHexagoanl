package com.api.proyectmanager.project.infrastructure.adapters.input.dtos.request;

import java.util.List;

public record ProjectCreateRequest(
    String name,
    String description,
    Integer leaderId,
    List<Integer> membersIds
) {}
