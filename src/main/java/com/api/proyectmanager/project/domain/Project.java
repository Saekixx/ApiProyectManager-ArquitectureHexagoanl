package com.api.proyectmanager.project.domain;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.api.proyectmanager.user.domain.User;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "project")
@Data
public class Project {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;

    // Relación con User (líder del proyecto)
    @ManyToOne
    @JoinColumn(name = "leader_id")
    private User leader;

    @CreationTimestamp
    @Column(updatable = false, name = "createdAt")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(insertable = false, name = "updatedAt")
    private LocalDateTime updatedAt;
}
