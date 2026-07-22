package com.api.proyectmanager.project.application.dtos;

import java.time.LocalDateTime;

public record MemberDTO(
    Integer id,
    String fullname,
    String email,
    String rol,
    Boolean isLeader,
    Boolean isActive,
    LocalDateTime joinedAt,
    LocalDateTime exitedAt
) {
    public MemberDTO(Integer id, String fullname, String email, String rol, Boolean isLeader, Boolean isActive, LocalDateTime joinedAt, LocalDateTime exitedAt) {
        this.id = id;
        this.fullname = fullname;
        this.email = email;
        this.rol = rol;
        this.isLeader = isLeader;
        this.isActive = isActive;
        this.joinedAt = joinedAt;
        this.exitedAt = exitedAt;
    }
}
