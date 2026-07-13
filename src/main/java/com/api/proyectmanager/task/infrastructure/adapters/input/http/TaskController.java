package com.api.proyectmanager.task.infrastructure.adapters.input.http;

import java.util.List;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.proyectmanager.shared.adapters.http.Response;
import com.api.proyectmanager.task.application.dto.MemberAction;
import com.api.proyectmanager.task.application.dto.TaskCreateRequest;
import com.api.proyectmanager.task.application.dto.TaskKanbanResponse;
import com.api.proyectmanager.task.application.dto.TaskUpdateRequest;
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
import com.api.proyectmanager.user.application.user.IdFindByEmail;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    private final FindByUserTask findByUserTaskService;
    // Casos de uso para asignar y remover miembros de una tarea
    private final AssignMember assignMemberService;
    private final RemoveMember removeMemberService;
    // Casos de uso para cambiar el estado de la tarea
    private final StartTask startTaskService;
    private final CompleteTask completeTaskService;
    private final ReopenTask reopenTaskService;
    // Casos de uso para el kanban board (agrupación de tareas por estado)
    private final KanbaBoard kanbanBoardService;
    private final IdFindByEmail idFindByEmailService; // Servicio para encontrar ID de usuario por email

    public TaskController(Save saveTaskService, Update updateTaskService, FindAll findAllService, FindById findByIdService, FindByIdProject findByIdProjectService, FindByUserTask findByUserTaskService, AssignMember assignMemberService, RemoveMember removeMemberService, StartTask startTaskService, CompleteTask completeTaskService, ReopenTask reopenTaskService, KanbaBoard kanbanBoardService, IdFindByEmail idFindByEmailService) {
        this.saveTaskService = saveTaskService;
        this.updateTaskService = updateTaskService;
        this.findAllService = findAllService;
        this.findByIdService = findByIdService;
        this.findByIdProjectService = findByIdProjectService;
        this.findByUserTaskService = findByUserTaskService;
        this.assignMemberService = assignMemberService;
        this.removeMemberService = removeMemberService;
        this.startTaskService = startTaskService;
        this.completeTaskService = completeTaskService;
        this.reopenTaskService = reopenTaskService;
        this.kanbanBoardService = kanbanBoardService;
        this.idFindByEmailService = idFindByEmailService;
    }

    // Endpoint para crear una nueva tarea
    // /api/tasks
    @PostMapping("/")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('COLABORADOR')")
    public ResponseEntity<Response<Task>> createTask(
        @RequestBody TaskCreateRequest dto,
        Authentication authentication
    ) {
        // Obtenemos el email del usuario autenticado
        String userEmail = authentication.getName();
        // Obtenemos el ID del usuario autenticado usando su email
        Integer currentUserId = idFindByEmailService.execute(userEmail);
        // Obtenemos el ID del proyecto y los IDs de los usuarios asignados desde la solicitud
        saveTaskService.execute(dto, currentUserId);
        return ResponseEntity.ok(new Response<>(true, "Tarea creada exitosamente."));
    }
    
    // Endpoint para actualizar una tarea existente
    // /api/tasks/12
    @PutMapping("/{taskId}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('COLABORADOR')")
    public ResponseEntity<Response<Task>> updateTask(
        @PathVariable Integer taskId, 
        @RequestBody TaskUpdateRequest dto
    ) {
        updateTaskService.execute(taskId, dto);
        return ResponseEntity.ok(new Response<>(true, "Tarea actualizada exitosamente."));
    }

    // Endpoint para asignar un miembro a una tarea
    // /api/tasks/12/assign-member
    @PostMapping("/{taskId}/assign-member")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('COLABORADOR')")
    public ResponseEntity<Response<Void>> assignMember(
        @PathVariable Integer taskId, 
        @RequestBody MemberAction dto,
        Authentication authentication
    ) {
        // Obtenemos el email del usuario autenticado
        String userEmail = authentication.getName();
        // Obtenemos el ID del usuario autenticado usando su email
        Integer creatorId = idFindByEmailService.execute(userEmail);
        // Ejecutamos el caso de uso para asignar el miembro a la tarea
        assignMemberService.execute(taskId, dto.memberId(), creatorId);
        return ResponseEntity.ok(new Response<>(true, "Miembro asignado a la tarea exitosamente."));
    }

    // Endpoint para remover un miembro de una tarea
    // /api/tasks/12/remove-member
    @PostMapping("/{taskId}/remove-member")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('COLABORADOR')")
    public ResponseEntity<Response<Void>> removeMember(
        @PathVariable Integer taskId, 
        @RequestBody MemberAction dto, 
        Authentication authentication
    ) {
        // Obtenemos el email del usuario autenticado
        String userEmail = authentication.getName();
        // Obtenemos el ID del usuario autenticado usando su email
        Integer editorId = idFindByEmailService.execute(userEmail);
        // Ejecutamos el caso de uso para remover el miembro de la tarea
        removeMemberService.execute(taskId, dto.memberId(), editorId);
        return ResponseEntity.ok(new Response<>(true, "Miembro removido de la tarea exitosamente."));
    }

    // Endpoint para iniciar una tarea
    // /api/tasks/12/start
    @PostMapping("/{taskId}/start")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('COLABORADOR')")
    public ResponseEntity<Response<Void>> startTask(
        @PathVariable Integer taskId, 
        Authentication authentication
    ) {
        // Obtenemos el email del usuario autenticado
        String userEmail = authentication.getName();
        // Obtenemos el ID del usuario autenticado usando su email
        Integer userId = idFindByEmailService.execute(userEmail);
        // Ejecutamos el caso de uso para iniciar la tarea
        startTaskService.execute(taskId, userId);
        return ResponseEntity.ok(new Response<>(true, "Tarea iniciada exitosamente."));
    }

    // Endpoint para completar una tarea
    // /api/tasks/12/complete
    @PostMapping("/{taskId}/complete")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('COLABORADOR')")
    public ResponseEntity<Response<Void>> completeTask(
        @PathVariable Integer taskId, 
        Authentication authentication
    ) {
        // Obtenemos el email del usuario autenticado
        String userEmail = authentication.getName();
        // Obtenemos el ID del usuario autenticado usando su email
        Integer userId = idFindByEmailService.execute(userEmail);
        // Ejecutamos el caso de uso para completar la tarea
        completeTaskService.execute(taskId, userId);
        return ResponseEntity.ok(new Response<>(true, "Tarea completada exitosamente."));
    }

    // Endpoint para reabrir una tarea
    // /api/tasks/12/reopen
    @PostMapping("/{taskId}/reopen")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('COLABORADOR')")
    public ResponseEntity<Response<Void>> reopenTask(
        @PathVariable Integer taskId, 
        Authentication authentication
    ) {
        // Obtenemos el email del usuario autenticado
        String userEmail = authentication.getName();
        // Obtenemos el ID del usuario autenticado usando su email
        Integer userId = idFindByEmailService.execute(userEmail);
        // Ejecutamos el caso de uso para reabrir la tarea
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
    // /api/tasks/12
    @GetMapping("/{id}")
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
    public ResponseEntity<Response<List<Task>>> findByAssignedUserId(Authentication authentication) {
        // Obtenemos el email del usuario autenticado
        String userEmail = authentication.getName();
        // Obtenemos el ID del usuario autenticado usando su email
        Integer userId = idFindByEmailService.execute(userEmail);
        return ResponseEntity.ok(new Response<>(true, "Tareas asignadas encontradas.", findByUserTaskService.execute(userId)));
    }


    // Endpoint para agrupar las tareas por estado (Pendiente, En Progreso, Completada y Atrasado) para un proyecto específico
    // /api/tasks/kanban/{projectId}
    @GetMapping("/kanban/{projectId}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('COLABORADOR')")
    public ResponseEntity<Response<TaskKanbanResponse>> getKanbanBoard(@PathVariable Integer projectId) {
        TaskKanbanResponse kanbanResponse = kanbanBoardService.execute(projectId);
        return ResponseEntity.ok(new Response<>(true, "Tablero Kanban encontrado.", kanbanResponse));
    }
}
