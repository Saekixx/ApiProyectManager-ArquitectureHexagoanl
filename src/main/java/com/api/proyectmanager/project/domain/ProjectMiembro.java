package com.api.proyectmanager.project.domain;

import com.api.proyectmanager.user.domain.User;
import java.time.LocalDateTime;




public class ProjectMiembro {
    private Project project; // Relación con Project
    private User user; // Relación con User
    private LocalDateTime joinedAt;

    public ProjectMiembro() {}

    public ProjectMiembro(Project project, User user, LocalDateTime joinedAt) {
        this.project = project;
        this.user = user;
        this.joinedAt = joinedAt;
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

    public LocalDateTime getJoinedAt() {
        return joinedAt;
    }

    public void setJoinedAt(LocalDateTime joinedAt) {
        this.joinedAt = joinedAt;
    }
}
