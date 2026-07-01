package com.api.proyectmanager.user.infrastructure.adapters.input.http;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.proyectmanager.shared.adapters.http.Response;
import com.api.proyectmanager.user.application.user.FindAllService;
import com.api.proyectmanager.user.application.user.UpdateService;
import com.api.proyectmanager.user.application.user.FindByIdService;
import com.api.proyectmanager.user.application.user.ToggleActiveById;
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
    private final FindAllService findAllService;
    private final UpdateService updateService;
    private final FindByIdService findByIdService;
    private final ToggleActiveById toggleActiveById;
    private final ChangeRoleById changeRoleByIdService;

    public UserController(FindAllService findAllService, UpdateService updateService, FindByIdService findByIdService, ToggleActiveById toggleActiveById, ChangeRoleById changeRoleByIdService) {
        this.findAllService = findAllService;
        this.updateService = updateService;
        this.findByIdService = findByIdService;
        this.toggleActiveById = toggleActiveById;
        this.changeRoleByIdService = changeRoleByIdService;
    }

    // Endpoint para obtener todos los usuarios
    @GetMapping("/")
    public ResponseEntity<Response<List<User>>> findAll() {
        return ResponseEntity.ok(new Response<>(true, "Usuarios encontrados.", findAllService.findAll()));
    }

    // Endpoint para obtener un usuario por su ID
    @GetMapping("/{id}")
    public ResponseEntity<Response<Optional<User>>> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(new Response<>(true, "Usuario encontrado.", findByIdService.findById(id)));
    }

    // Endpoint para editar un usuario por su ID
    @PutMapping("/edit/{id}")
    public ResponseEntity<Response<Void>> updateUserById(@PathVariable Integer id, @RequestBody User user) {
        updateService.execute(id, user);
        return ResponseEntity.ok(new Response<>(true, "Usuario actualizado correctamente."));
    }

    // Endpoint para activar o desactivar un usuario por su ID
    @PostMapping("/activate/{id}")
    public ResponseEntity<Response<Void>> activateUserById(@PathVariable Integer id) {
        String result = toggleActiveById.execute(id);
        return ResponseEntity.ok(new Response<>(true, result));
    }


    // Endpoint para cambiar el rol de un usuario por su ID
    @PostMapping("/rol/{userId}/{rolId}")
    public ResponseEntity<Response<Void>> changeRoleById(@PathVariable Integer userId, @PathVariable Integer rolId) {
        changeRoleByIdService.execute(userId, rolId);
        return ResponseEntity.ok(new Response<>(true, "Rol del usuario cambiado correctamente."));  
    }
}
