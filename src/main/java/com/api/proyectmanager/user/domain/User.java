package com.api.proyectmanager.user.domain;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "user")
@Data
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private String password;
    private String fullname;
    private String email;
    
    // Relación con Rol
    @ManyToOne
    @JoinColumn(name = "rol_id")
    private Rol rol;

    @CreationTimestamp
    @Column(updatable = false, name = "createdAt")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(insertable = false, name = "updatedAt")
    private LocalDateTime updatedAt;
}
