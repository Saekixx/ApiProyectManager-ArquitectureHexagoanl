package com.api.proyectmanager.task.infrastructure.adapters.output.jpa;

import java.time.LocalDateTime;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.api.proyectmanager.project.infrastructure.adapters.output.jpa.project.ProjectEntity;
import com.api.proyectmanager.task.domain.TaskStatus;
import com.api.proyectmanager.user.infrastructure.adapters.output.jpa.user.UserEntity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "task")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TaskEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private TaskStatus state; // Estado de la tarea (Patron State)
    @Column(nullable = false)
    private LocalDateTime dueDate; // Fecha de vencimiento de la tarea
    @Column(nullable = false)
    private Boolean isActive;

    // Relación con Project (la tarea pertenece a un proyecto)
    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private ProjectEntity project; // Relación con Project

    // Relación con User (usuarios asignados a la tarea)
    @ManyToMany
    @JoinTable(
        name = "task_user",
        joinColumns = @JoinColumn(name = "task_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<UserEntity> assignedUsers; 

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(insertable = false, name = "updated_at")
    private LocalDateTime updatedAt;
}
