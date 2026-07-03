package com.api.proyectmanager.task.domain;

import java.time.LocalDateTime;
import java.util.Set;

import com.api.proyectmanager.project.domain.Project;
import com.api.proyectmanager.user.domain.User;

public class Task {
    private Integer id;
    private String name;
    private String description;
    private TaskStatus state; // Estado de la tarea (Patron State)
    private LocalDateTime dueDate; // Fecha de vencimiento de la tarea
    private Boolean isActive;
    private Project project; // Relación con Project
    private Set<User> assignedUsers; // Relación con User (usuarios asignados a la tarea)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Task(){}

    public Task(Integer id, String name, String description, TaskStatus state, LocalDateTime dueDate,
                Boolean isActive, Project project, Set<User> assignedUsers, LocalDateTime createdAt,
                LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.state = state;
        this.dueDate = dueDate;
        this.isActive = isActive;
        this.project = project;
        this.assignedUsers = assignedUsers;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Método para comenzar el trabajo en la tarea, delegando la lógica al estado actual de la tarea
    public void comenzarTrabajo() {
        this.getState().iniciarProgreso(this);
    }

    // Método para finalizar el trabajo en la tarea, delegando la lógica al estado actual de la tarea
    public void finalizarTrabajo() {
        this.getState().completar(this);
    }

    // Metodo de uso interno para cambiar el estado de la tarea, usado por los estados del enum TaskStatus
    protected void changeState(TaskStatus nextState) {
        this.state = nextState;
    }

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Getter inteligente para el estado de la tarea, considerando la fecha de vencimiento
    public TaskStatus getState() {
        // Si la tarea no está completada y la fecha actual es posterior a la fecha de vencimiento, se considera atrasada
        if (this.state != TaskStatus.COMPLETADA && LocalDateTime.now().isAfter(this.dueDate)) {
            return TaskStatus.ATRASADO;
        }
        return this.state;
    }

    public void setState(TaskStatus state) {
        this.state = state;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Set<User> getAssignedUsers() {
        return assignedUsers;
    }

    public void setAssignedUsers(Set<User> assignedUsers) {
        this.assignedUsers = assignedUsers;
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
}
