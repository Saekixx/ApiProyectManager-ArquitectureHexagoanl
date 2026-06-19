package com.api.proyectmanager.entity.task;

import com.api.proyectmanager.entity.user.User;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "task_assignment")
@Data
public class TaskAssignment {
    @EmbeddedId
    private TaskAssignmentId id;

    @ManyToOne
    @MapsId("taskId")
    @JoinColumn(name = "task_id")
    private Task task;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;
}
