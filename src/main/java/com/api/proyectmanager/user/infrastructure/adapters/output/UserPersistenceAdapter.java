package com.api.proyectmanager.user.infrastructure.adapters.output;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.api.proyectmanager.user.domain.User;
import com.api.proyectmanager.user.domain.ports.UserRepository;
import com.api.proyectmanager.user.infrastructure.adapters.output.jpa.user.SpringDataUserRepository;
import com.api.proyectmanager.user.infrastructure.adapters.output.jpa.user.UserEntity;
import com.api.proyectmanager.user.infrastructure.adapters.output.mapper.user.UserMapper;

// Adaptador para la persistencia de usuarios
@Component
public class UserPersistenceAdapter implements UserRepository {
    private final SpringDataUserRepository jpaRepository; // Repositorio JPA para la persistencia de usuarios

    // Inyección por constructor
    public UserPersistenceAdapter(SpringDataUserRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    /* Este adaptador implementa los métodos del puerto UserRepository para 
       interactuar con la base de datos a través del repositorio JPA */

    @Override
    @Transactional
    public User save(User user) {
        // Convertimos el objeto User a UserEntity usando el mapper
        UserEntity entity = UserMapper.toEntity(user);
        // Guardamos la entidad en la base de datos usando el repositorio JPA
        UserEntity savedEntity = jpaRepository.save(entity);
        // Convertimos la entidad guardada de nuevo a User y la retornamos
        return UserMapper.toDomain(savedEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean existsByEmail(String email) {
        // Verificamos si existe un usuario con el correo electrónico dado usando el repositorio JPA
        return jpaRepository.findByEmail(email).isPresent();
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        // Obtenemos todas las entidades de usuario desde la base de datos usando el repositorio JPA
        List<UserEntity> entities = jpaRepository.findAll();
        // Convertimos la lista de entidades a una lista de objetos User y la retornamos
        return entities.stream().map(UserMapper::toDomain).toList();
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByEmail(String email) {
        // Buscamos la entidad de usuario por su correo electrónico usando el repositorio JPA
        return jpaRepository.findByEmail(email)
                .map(UserMapper::toDomain); // Convertimos la entidad encontrada a User y la retornamos envuelta en Optional
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByUsername(String username) {
        // Buscamos la entidad de usuario por su nombre de usuario usando el repositorio JPA
        return jpaRepository.findByUsername(username)
                .map(UserMapper::toDomain); // Convertimos la entidad encontrada a User y la retornamos envuelta en Optional
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean existsByUsername(String username) {
        // Verificamos si existe un usuario con el nombre de usuario dado usando el repositorio JPA
        return jpaRepository.findByUsername(username).isPresent();
    }

    @Override 
    @Transactional(readOnly = true)
    public Optional<User> findById(Integer id) {
        // Buscamos la entidad de usuario por su ID usando el repositorio JPA
        return jpaRepository.findById(id)
                .map(UserMapper::toDomain); // Convertimos la entidad encontrada a User y la retornamos envuelta en Optional
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean isAdmin(Integer userId) {
        // Verificamos si el usuario con el ID dado tiene el rol de admin usando el repositorio JPA
        if (userId == null) return false;
        // Retornamos true si existe un usuario con el ID dado y su rol es "ADMIN", de lo contrario retornamos false
        return jpaRepository.existsByIdAndRole_Name(userId, "ADMIN");
    }
}
