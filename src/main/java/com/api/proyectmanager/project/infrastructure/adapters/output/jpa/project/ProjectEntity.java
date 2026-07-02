package com.api.proyectmanager.project.infrastructure.adapters.output.jpa.project;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.api.proyectmanager.user.infrastructure.adapters.output.jpa.user.UserEntity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "project")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProjectEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity leader; // Relación con la clase UserEntity (líder del proyecto)
    @Column(nullable = false)
    private Boolean isActive = true;  

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(insertable = false, name = "updated_at")
    private LocalDateTime updatedAt;
}
