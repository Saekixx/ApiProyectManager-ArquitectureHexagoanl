package com.api.proyectmanager.entity.project;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.api.proyectmanager.entity.user.User;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "project_miembro")
@Data
public class ProjectMiembro {
    @EmbeddedId
    private ProjectMiembroId id;

    @ManyToOne
    @MapsId("projectId")
    @JoinColumn(name = "project_id")
    private Project project;
    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;
    
    @CreationTimestamp
    @Column(updatable = false, name = "joined_at")
    private LocalDateTime joinedAt;
}
