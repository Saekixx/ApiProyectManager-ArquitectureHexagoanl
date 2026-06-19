package com.api.proyectmanager.entity.project;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.api.proyectmanager.entity.user.User;

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
