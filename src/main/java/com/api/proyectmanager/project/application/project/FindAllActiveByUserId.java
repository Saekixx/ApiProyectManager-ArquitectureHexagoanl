package com.api.proyectmanager.project.application.project;

import java.util.List;

import org.springframework.stereotype.Service;

import com.api.proyectmanager.project.domain.Project;
import com.api.proyectmanager.project.domain.ports.ProjectRepository;

@Service
public class FindAllActiveByUserId {
    private final ProjectRepository projectRepository;
    
    public FindAllActiveByUserId(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public List<Project> execute(Integer userId) {
        return projectRepository.findAllActiveByUserId(userId);
    }
}
