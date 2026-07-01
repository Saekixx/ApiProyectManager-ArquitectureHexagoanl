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
    public void actualizarDatosGenerales(String username, String email, String fullname, String password) {
        if (username != null && !username.isBlank()) this.username = username;
        if (email != null && !email.isBlank()) this.email = email;
        if (fullname != null && !fullname.isBlank()) this.fullname = fullname;
        if (password != null && !password.isBlank()) this.password = password;
    }
    // Metodo para activar o desactivar el usuario
    public Boolean toggleActive() {
        this.isActive = !this.isActive;
        this.updatedAt = LocalDateTime.now(); // Actualizar la fecha de actualización al cambiar el estado
        return this.isActive;
    }
}
