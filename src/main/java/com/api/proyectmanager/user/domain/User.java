package com.api.proyectmanager.user.domain;

import java.time.LocalDateTime;

public class User {
    private Integer id;
    private String username;
    private String password;
    private String fullname;
    private String email;
    private Boolean isActive = true;
    private Rol rol; // Relación con la clase Rol
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public User() {}

    public User(Integer id, String username, String password, String fullname, String email, Boolean isActive, Rol rol,
            LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.email = email;
        this.isActive = isActive;
        this.rol = rol;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
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

    // Método para actualizar los datos del usuario, excepto el rol y el estado activo
    public void actualizarDatos(User cambios) {
        // Validamos que no se intente cambiar el rol directamente
        if (cambios.getRol() != null) {
            throw new IllegalArgumentException("No se puede cambiar el rol directamente.");
        }
        // Validamos que no se intente cambiar el estado activo directamente
        if (cambios.getIsActive() != null && !cambios.getIsActive().equals(this.getIsActive())) { 
            throw new IllegalArgumentException("No se puede cambiar el estado activo directamente.");
        }

        if (cambios.getUsername() != null) this.username = cambios.getUsername();
        if (cambios.getEmail() != null) this.email = cambios.getEmail();
        if (cambios.getFullname() != null) this.fullname = cambios.getFullname();
        if (cambios.getPassword() != null) this.password = cambios.getPassword();
    }
}
