package com.api.proyectmanager.project.domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import com.api.proyectmanager.user.domain.User;

public class Project {
    private Integer id;
    private String name;
    private String description;
    private Boolean isActive;
    private User leader; // Relación con User (líder del proyecto)
    private Set<ProjectMiembro> projectMembers;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Project() {}

    public Project(Integer id, String name, String description, Boolean isActive, User leader, Set<ProjectMiembro> projectMembers, LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.isActive = isActive;
        this.leader = leader;
        this.projectMembers = projectMembers;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isActive() {
        return isActive;
    }

    public void setActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public User getLeader() {
        return leader;
    }

    public void setLeader(User leader) {
        this.leader = leader;
    }

    public Set<ProjectMiembro> getProjectMembers() {
        return projectMembers;
    }

    public void setProjectMembers(Set<ProjectMiembro> projectMembers) {
        this.projectMembers = projectMembers;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Boolean toggleActive() {
        this.isActive = !this.isActive;
        this.updatedAt = LocalDateTime.now();
        return this.isActive;
    }
}
