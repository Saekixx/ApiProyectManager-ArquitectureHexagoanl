package com.api.proyectmanager.project.domain;

import com.api.proyectmanager.user.domain.User;
import java.time.LocalDateTime;

public class ProjectMiembro {
    private Integer id;
    private Project project; // Relación con Project
    private User user; // Relación con User
    private Boolean isActive; // Indica si el miembro está activo en el proyecto
    private LocalDateTime joinedAt;
    private LocalDateTime exitedAt;

    public ProjectMiembro() {}

    public ProjectMiembro(Integer id, Project project, User user, Boolean isActive, LocalDateTime joinedAt, LocalDateTime exitedAt) {
        this.id = id;
        this.project = project;
        this.user = user;
        this.isActive = isActive;
        this.joinedAt = joinedAt;
        this.exitedAt = exitedAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public LocalDateTime getJoinedAt() {
        return joinedAt;
    }

    public void setJoinedAt(LocalDateTime joinedAt) {
        this.joinedAt = joinedAt;
    }
    
    public LocalDateTime getExitedAt() {
        return exitedAt;
    }

    public void setExitedAt(LocalDateTime exitedAt) {
        this.exitedAt = exitedAt;
    }

    public Boolean toggleActive() {
        this.isActive = !this.isActive;
        if (!this.isActive) {
            this.exitedAt = LocalDateTime.now(); // Actualizar la fecha de salida al desactivar
        } else {
            this.exitedAt = null; // Limpiar la fecha de salida al activar
        }
        return this.isActive;
    }
}
