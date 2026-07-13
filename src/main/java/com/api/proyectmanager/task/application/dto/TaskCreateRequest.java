package com.api.proyectmanager.task.application.dto;

import java.time.LocalDateTime;
import java.util.Set;

public record TaskCreateRequest(
    Integer projectId,
    String title,
    String description,
    LocalDateTime dueDate,
    Set<Integer> membersIds
) {}
