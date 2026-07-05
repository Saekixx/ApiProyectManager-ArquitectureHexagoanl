package com.api.proyectmanager.task.infrastructure.adapters.output;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.api.proyectmanager.task.domain.Task;
import com.api.proyectmanager.task.domain.ports.TaskRepository;
import com.api.proyectmanager.task.infrastructure.adapters.output.jpa.SpringDataTaskRepository;
import com.api.proyectmanager.task.infrastructure.adapters.output.jpa.TaskEntity;
import com.api.proyectmanager.task.infrastructure.adapters.output.mapper.TaskMapper;

@Component
public class TaskPersistenceAdapter implements TaskRepository {
    private final SpringDataTaskRepository jpaRepository; // Repositorio JPA para la persistencia de tareas

    // Inyección por constructor
    public TaskPersistenceAdapter(SpringDataTaskRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    @Transactional
    public Task save(Task task) {
        // Convertimos el objeto Task a TaskEntity usando el mapper
        TaskEntity entity = TaskMapper.toEntity(task);
        // Guardamos la entidad en la base de datos usando el repositorio JPA
        jpaRepository.save(entity);
        // Devolvemos el objeto Task actualizado
        return TaskMapper.toDomain(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Task> findAll() {
        // Obtenemos todas las entidades de tarea desde la base de datos usando el repositorio JPA
        List<TaskEntity> entities = jpaRepository.findAll();
        // Convertimos la lista de entidades a una lista de objetos Task y la retornamos
        return entities.stream().map(TaskMapper::toDomain).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Task> findById(Integer id) {
        // Buscamos la entidad de tarea por su ID usando el repositorio JPA
        Optional<TaskEntity> entityOptional = jpaRepository.findById(id);
        // Convertimos el objeto TaskEntity a Task si está presente
        return entityOptional.map(TaskMapper::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Task> findByProjectId(Integer projectId) {
        // Buscamos la entidad de tarea por el ID del proyecto usando el repositorio JPA
        List<TaskEntity> entities = jpaRepository.findByProjectId(projectId);
        // Convertimos la lista de entidades a una lista de objetos Task y la retornamos
        return entities.stream().map(TaskMapper::toDomain).toList();
    }

    @Override
    @Transactional
    public void deactivateTask(Integer taskId) {
        // Buscamos la entidad de tarea por su ID usando el repositorio JPA
        TaskEntity entity = jpaRepository.findById(taskId).orElse(null);
        if (entity != null) {
            // Desactivamos la tarea estableciendo su estado a inactivo
            entity.setIsActive(false);
            // Guardamos la entidad actualizada en la base de datos usando el repositorio JPA
            jpaRepository.save(entity);
        }
    }
}
