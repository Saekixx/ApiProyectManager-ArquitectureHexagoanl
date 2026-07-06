package com.api.proyectmanager.task.application.dto;

import java.util.List;
import java.util.Map;

import com.api.proyectmanager.task.domain.Task;

public class TaskKanbanResponse {
    private List<Task> pendiente;
    private List<Task> enProgreso;
    private List<Task> completada;
    private List<Task> atrasado;

    // Constructor vacío
    public TaskKanbanResponse() {}

    // Constructor que recibe un mapa de tareas agrupadas por estado
    public TaskKanbanResponse(Map<String, List<Task>> groupedTasks) {
        this.pendiente = groupedTasks.get("Pendiente");
        this.enProgreso = groupedTasks.get("En Progreso");
        this.completada = groupedTasks.get("Completada");
        this.atrasado = groupedTasks.get("Atrasado");
    }

    // Getters para cada lista de tareas
    public List<Task> getPendiente() { return pendiente; }
    public List<Task> getEnProgreso() { return enProgreso; }
    public List<Task> getCompletada() { return completada; }
    public List<Task> getAtrasado() { return atrasado; }

    // Setters para cada lista de tareas
    public void setPendiente(List<Task> pendiente) { this.pendiente = pendiente; }
    public void setEnProgreso(List<Task> enProgreso) { this.enProgreso = enProgreso; }
    public void setCompletada(List<Task> completada) { this.completada = completada; }
    public void setAtrasado(List<Task> atrasado) { this.atrasado = atrasado; }
}
