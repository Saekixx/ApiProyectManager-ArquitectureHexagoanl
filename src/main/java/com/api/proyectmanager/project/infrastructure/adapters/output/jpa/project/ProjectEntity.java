package com.api.proyectmanager.project.infrastructure.adapters.output.jpa.project;

import java.time.LocalDateTime;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.api.proyectmanager.project.infrastructure.adapters.output.jpa.projectMiembro.ProjectMiembroEntity;
import com.api.proyectmanager.user.infrastructure.adapters.output.jpa.user.UserEntity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "project")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProjectEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private Boolean isActive; 

    @ManyToOne
    @JoinColumn(name = "leader_id")
    private UserEntity leader; // Relación con la clase UserEntity (líder del proyecto)

    @OneToMany(mappedBy = "project") // Relación con la clase ProjectMiembroEntity (miembros del proyecto)
    private Set<ProjectMiembroEntity> projectMembers;

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(insertable = false, name = "updated_at")
    private LocalDateTime updatedAt;
}
