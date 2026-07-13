package com.api.proyectmanager.task.application.dto;

import java.time.LocalDateTime;

public record TaskUpdateRequest(
    String title,
    String description,
    LocalDateTime dueDate,
    Integer creatorId
) {}