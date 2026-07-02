package com.api.proyectmanager.project.infrastructure.adapters.output.jpa.projectMiembro;

import java.time.LocalDateTime;

import com.api.proyectmanager.project.infrastructure.adapters.output.jpa.project.ProjectEntity;
import com.api.proyectmanager.user.infrastructure.adapters.output.jpa.user.UserEntity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "project_miembro")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProjectMiembroEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "project")
    private ProjectEntity project; // Relación con Project
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user; // Relación con User
    @Column
    private Boolean isActive = true; // Indica si el miembro está activo en el proyecto
    @Column(name = "joined_at")
    private LocalDateTime joinedAt;
    @Column(name = "exited_at")
    private LocalDateTime exitedAt;
}
