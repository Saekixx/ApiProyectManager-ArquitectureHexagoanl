package com.api.proyectmanager.task.domain;

import com.api.proyectmanager.shared.domain.BusinessException;

public interface TaskState {
    // Permite iniciar el progreso de la tarea
    void iniciarProgreso(Task task) throws BusinessException;
    // Permite completar la tarea
    void completar(Task task) throws BusinessException;

    // Permite ver el estado actual de la tarea
    TaskStatus getStatus();
}
