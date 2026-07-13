package com.api.proyectmanager.user.infrastructure.adapters.input.http;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.proyectmanager.shared.adapters.http.Response;
import com.api.proyectmanager.user.application.user.FindAll;
import com.api.proyectmanager.user.application.user.Update;
import com.api.proyectmanager.user.application.user.FindById;
import com.api.proyectmanager.user.application.user.Save;
import com.api.proyectmanager.user.application.user.ToggleActiveById;
import com.api.proyectmanager.user.application.dtos.ChangeRolRequest;
import com.api.proyectmanager.user.application.dtos.UserCreateRequest;
import com.api.proyectmanager.user.application.dtos.UserUpdateRequest;
import com.api.proyectmanager.user.application.user.ChangeRoleById;
import com.api.proyectmanager.user.domain.User;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {
    private final FindAll findAllService;
    private final Save saveService;
    private final Update updateService;
    private final FindById findByIdService;
    private final ToggleActiveById toggleActiveById;
    private final ChangeRoleById changeRoleByIdService;

    public UserController(FindAll findAllService, Save saveService, Update updateService, FindById findByIdService, ToggleActiveById toggleActiveById, ChangeRoleById changeRoleByIdService) {
        this.findAllService = findAllService;
        this.saveService = saveService;
        this.updateService = updateService;
        this.findByIdService = findByIdService;
        this.toggleActiveById = toggleActiveById;
        this.changeRoleByIdService = changeRoleByIdService;
    }

    // Endpoint para obtener todos los usuarios
    // /api/users/
    @GetMapping("/")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response<List<User>>> findAll() {
        return ResponseEntity.ok(new Response<>(true, "Usuarios encontrados.", findAllService.findAll()));
    }

    // Endpoint para obtener un usuario por su ID
    // /api/users/{id}
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response<Optional<User>>> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(new Response<>(true, "Usuario encontrado.", findByIdService.findById(id)));
    }

    // Endpoint para crear un nuevo usuario
    // /api/users/
    @PostMapping("/")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response<String>> createUser(@RequestBody UserCreateRequest user) {
        String result = saveService.save(user);
        return ResponseEntity.ok(new Response<>(true, result));
    }

    // Endpoint para editar un usuario por su ID
    // /api/users/{id}
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response<Void>> updateUserById(@PathVariable Integer id, @RequestBody UserUpdateRequest user) {
        updateService.execute(id, user);
        return ResponseEntity.ok(new Response<>(true, "Usuario actualizado correctamente."));
    }

    // Endpoint para activar o desactivar un usuario por su ID
    @PostMapping("/activate/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response<Void>> activateUserById(@PathVariable Integer id) {
        String result = toggleActiveById.execute(id);
        return ResponseEntity.ok(new Response<>(true, result));
    }


    // Endpoint para cambiar el rol de un usuario por su ID
    @PutMapping("/{userId}/rol")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response<Void>> changeRoleById(@PathVariable Integer userId, @RequestBody ChangeRolRequest request) {
        changeRoleByIdService.execute(userId, request);
        return ResponseEntity.ok(new Response<>(true, "Rol del usuario cambiado correctamente."));  
    }
}
