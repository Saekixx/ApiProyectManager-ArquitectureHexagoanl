package com.api.proyectmanager.task.application.usecases;

import java.util.List;

import com.api.proyectmanager.shared.domain.annotation.UseCase;
import com.api.proyectmanager.task.application.dto.TaskKanbanResponse;
import com.api.proyectmanager.task.domain.Task;
import com.api.proyectmanager.task.domain.TaskStatus;

@UseCase
public class KanbaBoard {
    private final FindByIdProject findByIdProjectUseCase;

    public KanbaBoard(FindByIdProject findByIdProjectUseCase) {
        this.findByIdProjectUseCase = findByIdProjectUseCase;
    }

    public TaskKanbanResponse execute(Integer projectId) {
        // Obtener todas las tareas asociadas al proyecto
        List<Task> tasks = findByIdProjectUseCase.execute(projectId);

        // Crear un objeto TaskKanbanResponse para agrupar las tareas por estado
        TaskKanbanResponse kanbanResponse = new TaskKanbanResponse();
        kanbanResponse.setPendiente(tasks.stream().filter(task -> task.getState() == TaskStatus.PENDIENTE).toList());
        kanbanResponse.setEnProgreso(tasks.stream().filter(task -> task.getState() == TaskStatus.EN_PROGRESO).toList());
        kanbanResponse.setCompletada(tasks.stream().filter(task -> task.getState() == TaskStatus.COMPLETADA).toList());
        kanbanResponse.setAtrasado(tasks.stream().filter(task -> task.getState() == TaskStatus.ATRASADO).toList());

        return kanbanResponse;
    }
}
