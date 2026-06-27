package com.api.proyectmanager.user.infrastructure.adapters.output;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.api.proyectmanager.user.domain.Rol;
import com.api.proyectmanager.user.domain.ports.RolRepository;
import com.api.proyectmanager.user.infrastructure.adapters.output.jpa.rol.SpringDataRolRepository;
import com.api.proyectmanager.user.infrastructure.adapters.output.mapper.rol.RolMapper;

// Adaptador para la persistencia de roles
@Component
public class RolPersistenceAdapter implements RolRepository {
    private final SpringDataRolRepository rolRepository; // Inyección de dependencia del repositorio de roles

    // Constructor para inyectar el repositorio de roles
    public RolPersistenceAdapter(SpringDataRolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    /* Este adaptador implementa los métodos del puerto RolRepository para 
       interactuar con la base de datos a través del repositorio JPA */ 

    @Override
    public List<Rol> findAll() {
        return rolRepository.findAll()
                .stream()
                .map(RolMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Rol> findById(Integer id) {
        return rolRepository.findById(id)
                .map(RolMapper::toDomain); 
    }

    @Override
    public Optional<Rol> findByName(String name) {
        return rolRepository.findByName(name)
                .map(RolMapper::toDomain); 
    }

}
