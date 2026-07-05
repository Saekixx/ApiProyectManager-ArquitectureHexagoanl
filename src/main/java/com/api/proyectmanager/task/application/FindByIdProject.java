package com.api.proyectmanager.task.application;

import java.util.List;

import org.springframework.stereotype.Service;

import com.api.proyectmanager.task.domain.Task;
import com.api.proyectmanager.task.domain.ports.TaskRepository;

@Service("TaskFindByIdProject")
public class FindByIdProject {
    private final TaskRepository taskRepository;

    public FindByIdProject(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> execute(Integer projectId) {
        return taskRepository.findByProjectId(projectId);
    }
}
