package com.api.proyectmanager.task.infrastructure.adapters.input.http;

import java.util.List;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.proyectmanager.shared.adapters.http.Response;
import com.api.proyectmanager.task.application.dto.TaskKanbanResponse;
import com.api.proyectmanager.task.application.usecases.AssignMember;
import com.api.proyectmanager.task.application.usecases.CompleteTask;
import com.api.proyectmanager.task.application.usecases.FindAll;
import com.api.proyectmanager.task.application.usecases.FindById;
import com.api.proyectmanager.task.application.usecases.FindByIdProject;
import com.api.proyectmanager.task.application.usecases.FindByUserTask;
import com.api.proyectmanager.task.application.usecases.KanbaBoard;
import com.api.proyectmanager.task.application.usecases.RemoveMember;
import com.api.proyectmanager.task.application.usecases.ReopenTask;
import com.api.proyectmanager.task.application.usecases.Save;
import com.api.proyectmanager.task.application.usecases.StartTask;
import com.api.proyectmanager.task.application.usecases.Update;
import com.api.proyectmanager.task.domain.Task;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "*")
public class TaskController {
    // Casos de uso para crear y actualizar tareas
    private final Save saveTaskService;
    private final Update updateTaskService;
    // Casos de uso para obtener tareas (lectura)
    private final FindAll findAllService;
    private final FindById findByIdService;
    private final FindByIdProject findByIdProjectService;
    private final FindByUserTask findByUserService;
    // Casos de uso para asignar y remover miembros de una tarea
    private final AssignMember assignMemberService;
    private final RemoveMember removeMemberService;
    // Casos de uso para cambiar el estado de la tarea
    private final StartTask startTaskService;
    private final CompleteTask completeTaskService;
    private final ReopenTask reopenTaskService;
    // Casos de uso para el kanban board (agrupación de tareas por estado)
    private final KanbaBoard kanbanBoardService;

    public TaskController(Save saveTaskService, Update updateTaskService, FindAll findAllService, FindById findByIdService, FindByIdProject findByIdProjectService, FindByUserTask findByUserService, AssignMember assignMemberService, RemoveMember removeMemberService, StartTask startTaskService, CompleteTask completeTaskService, ReopenTask reopenTaskService, KanbaBoard kanbanBoardService) {
        this.saveTaskService = saveTaskService;
        this.updateTaskService = updateTaskService;
        this.findAllService = findAllService;
        this.findByIdService = findByIdService;
        this.findByIdProjectService = findByIdProjectService;
        this.findByUserService = findByUserService;
        this.assignMemberService = assignMemberService;
        this.removeMemberService = removeMemberService;
        this.startTaskService = startTaskService;
        this.completeTaskService = completeTaskService;
        this.reopenTaskService = reopenTaskService;
        this.kanbanBoardService = kanbanBoardService;
    }

    // Endpoint para crear una nueva tarea
    // /api/tasks/create?creatorId=10&projectId=5&assignedUserId=3,4,5
    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('COLABORADOR')")
    public ResponseEntity<Response<Task>> createTask(
        @RequestBody Task task, 
        @RequestParam Integer creatorId, 
        @RequestParam Integer projectId, 
        @RequestParam Set<Integer> assignedUserId
    ) {
        saveTaskService.execute(task, projectId, assignedUserId, creatorId);
        return ResponseEntity.ok(new Response<>(true, "Tarea creada exitosamente."));
    }
    
    // Endpoint para actualizar una tarea existente
    // /api/tasks/update/12/project/5?creatorId=10
    @PutMapping("/update/{taskId}/project/{projectId}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('COLABORADOR')")
    public ResponseEntity<Response<Task>> updateTask(
        @PathVariable Integer taskId, 
        @PathVariable Integer projectId, 
        @RequestParam Integer creatorId,
        @RequestBody Task task
    ) {
        updateTaskService.execute(taskId, projectId, task, creatorId);
        return ResponseEntity.ok(new Response<>(true, "Tarea actualizada exitosamente."));
    }

    // Endpoint para asignar un miembro a una tarea
    // /api/tasks/assign-member/12/34?creatorId=10
    @PostMapping("/assign-member/{taskId}/{memberId}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('COLABORADOR')")
    public ResponseEntity<Response<Void>> assignMember(
        @PathVariable Integer taskId, 
        @PathVariable Integer memberId,
        @RequestParam Integer creatorId
    ) {
        assignMemberService.execute(taskId, memberId, creatorId);
        return ResponseEntity.ok(new Response<>(true, "Miembro asignado a la tarea exitosamente."));
    }

    // Endpoint para remover un miembro de una tarea
    // /api/tasks/remove-member/12/34?editorId=10
    @PostMapping("/remove-member/{taskId}/{memberId}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('COLABORADOR')")
    public ResponseEntity<Response<Void>> removeMember(
        @PathVariable Integer taskId, 
        @PathVariable Integer memberId, 
        @RequestParam Integer editorId
    ) {
        removeMemberService.execute(taskId, memberId, editorId);
        return ResponseEntity.ok(new Response<>(true, "Miembro removido de la tarea exitosamente."));
    }

    // Endpoint para iniciar una tarea
    // /api/tasks/start/12?userId=10
    @PostMapping("/start/{taskId}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('COLABORADOR')")
    public ResponseEntity<Response<Void>> startTask(
        @PathVariable Integer taskId, 
        @RequestParam Integer userId
    ) {
        startTaskService.execute(taskId, userId);
        return ResponseEntity.ok(new Response<>(true, "Tarea iniciada exitosamente."));
    }

    // Endpoint para completar una tarea
    // /api/tasks/complete/12?userId=10
    @PostMapping("/complete/{taskId}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('COLABORADOR')")
    public ResponseEntity<Response<Void>> completeTask(
        @PathVariable Integer taskId, 
        @RequestParam Integer userId
    ) {
        completeTaskService.execute(taskId, userId);
        return ResponseEntity.ok(new Response<>(true, "Tarea completada exitosamente."));
    }

    // Endpoint para reabrir una tarea
    // /api/tasks/reopen/12?userId=10
    @PostMapping("/reopen/{taskId}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('COLABORADOR')")
    public ResponseEntity<Response<Void>> reopenTask(
        @PathVariable Integer taskId, 
        @RequestParam Integer userId
    ) {
        reopenTaskService.execute(taskId, userId);
        return ResponseEntity.ok(new Response<>(true, "Tarea reabierta exitosamente."));
    }

    // Endpoint para obtener todas las tareas
    // /api/tasks/
    @GetMapping("/")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('COLABORADOR')")
    public ResponseEntity<Response<List<Task>>> findAll() {
        return ResponseEntity.ok(new Response<>(true, "Tareas encontradas.", findAllService.execute()));
    }

    // Endpoint para obtener una tarea por su ID
    // /api/tasks/task/12
    @GetMapping("/task/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('COLABORADOR')")
    public ResponseEntity<Response<Task>> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(new Response<>(true, "Tarea encontrada.", findByIdService.execute(id)));
    }

    // Endpoint para obtener todas las tareas de un proyecto por su ID
    // /api/tasks/project/5
    @GetMapping("/project/{projectId}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('COLABORADOR')")
    public ResponseEntity<Response<List<Task>>> findByProjectId(@PathVariable Integer projectId) {
        return ResponseEntity.ok(new Response<>(true, "Tareas del proyecto encontradas.", findByIdProjectService.execute(projectId)));
    }

    // Endpoint para obtener todas las tareas asignadas por el usuario autenticado
    // /api/tasks/my-tasks
    @GetMapping("/my-tasks")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('COLABORADOR')")
    public ResponseEntity<Response<List<Task>>> findByAssignedUserId(@RequestParam Integer userId) {
        return ResponseEntity.ok(new Response<>(true, "Tareas asignadas encontradas.", findByUserService.execute(userId)));
    }


    // Endpoint para agrupar las tareas por estado (Pendiente, En Progreso, Completada y Atrasado) para un proyecto específico
    // /api/tasks/project/{projectId}/kanban
    @GetMapping("/project/{projectId}/kanban")
    public ResponseEntity<Response<TaskKanbanResponse>> getKanbanBoard(@PathVariable Integer projectId) {
        TaskKanbanResponse kanbanResponse = kanbanBoardService.execute(projectId);
        return ResponseEntity.ok(new Response<>(true, "Tablero Kanban encontrado.", kanbanResponse));
    }
}
