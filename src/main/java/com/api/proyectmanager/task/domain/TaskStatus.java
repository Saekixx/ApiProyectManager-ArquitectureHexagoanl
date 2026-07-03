package com.api.proyectmanager.task.domain;

import com.api.proyectmanager.shared.domain.BusinessException;

public enum TaskStatus implements TaskState {
    PENDIENTE {
        @Override
        public void iniciarProgreso(Task task) {
            // Regla de negocio: Solo se puede iniciar el progreso si la tarea está pendiente
            task.changeState(EN_PROGRESO);
        }

        @Override
        public void completar(Task task) {
            // Regla de negocio: No se puede saltar de Pendiente a Completada directamente
            throw new BusinessException("No puedes completar una tarea que aún está 'PENDIENTE'. Debe pasar a 'EN_PROGRESO' primero.");
        }

        @Override
        public void reabrir(Task task) {
            // Regla de negocio: No se puede reabrir una tarea que está pendiente
            throw new BusinessException("No puedes reabrir una tarea que aún está 'PENDIENTE'.");
        }

        @Override
        public TaskStatus getStatus() { return PENDIENTE; } // Mapea al valor base del Enum
    },

    EN_PROGRESO {
        @Override
        public void iniciarProgreso(Task task) {
            // Regla de negocio: No se puede iniciar el progreso si ya está en progreso
            throw new BusinessException("La tarea ya se encuentra 'EN_PROGRESO'.");
        }

        @Override
        public void completar(Task task) {
            // Regla de negocio : Solo se puede completar si está en progreso
            task.changeState(COMPLETADA);
        }

        @Override
        public void reabrir(Task task) {
            // Regla de negocio: No se puede reabrir una tarea que está en progreso
            throw new BusinessException("No puedes reabrir una tarea que aún está 'EN_PROGRESO'.");
        }

        @Override
        public TaskStatus getStatus() { return EN_PROGRESO; } // Mapea al valor base del Enum
    },

    COMPLETADA {
        @Override
        public void iniciarProgreso(Task task) {
            // Regla de negocio: No se puede iniciar el progreso si ya está completada
            throw new BusinessException("No puedes modificar una tarea que ya está 'COMPLETADA'.");
        }

        @Override
        public void completar(Task task) {
            // Regla de negocio: No se puede completar si ya está completada
            throw new BusinessException("La tarea ya ha sido 'COMPLETADA' previamente.");
        }

        @Override
        public void reabrir(Task task) {
            // Regla de negocio: Solo se puede reabrir si está completada
            task.changeState(EN_PROGRESO);
        }

        @Override
        public TaskStatus getStatus() { return COMPLETADA; } // Mapea al valor base del Enum
    },

    ATRASADO {
        @Override
        public void iniciarProgreso(Task task) {
            // Regla de negocio: Si la tarea está atrasada, aún se puede iniciar el progreso
            task.changeState(EN_PROGRESO);
        }

        @Override
        public void completar(Task task) {
            // Regla de negocio: Si el tiempo venció y nunca la iniciaron, no pueden completarla de golpe
            throw new BusinessException("La tarea está 'ATRASADA' y no fue iniciada a tiempo. Pásala a 'EN_PROGRESO' para trabajar en ella.");
        }

        @Override
        public void reabrir(Task task) {
            // Regla de negocio: No se puede reabrir una tarea atrasada directamente, debe pasar a en progreso primero
            throw new BusinessException("No puedes reabrir una tarea que está 'ATRASADA'. Debe pasar a 'EN_PROGRESO' primero.");
        }

        @Override
        public TaskStatus getStatus() { return ATRASADO; }
    };

    // Declaración base del método del enum para que compile
    public abstract TaskStatus getStatus();
}
