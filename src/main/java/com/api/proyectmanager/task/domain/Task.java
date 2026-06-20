package com.api.proyectmanager.task.domain;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.api.proyectmanager.project.domain.Project;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "task")
@Data
public class Task {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;

    // Relación con Project
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @CreationTimestamp
    @Column(updatable = false, name = "createdAt")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(insertable = false, name = "updatedAt")
    private LocalDateTime updatedAt;
}
