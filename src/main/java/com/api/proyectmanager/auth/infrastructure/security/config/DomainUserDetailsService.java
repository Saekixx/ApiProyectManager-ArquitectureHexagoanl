package com.api.proyectmanager.auth.infrastructure.security.config;

import org.springframework.stereotype.Service;

import com.api.proyectmanager.user.domain.User;
import com.api.proyectmanager.user.domain.ports.UserRepository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Service
public class DomainUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    // Constructor 
    public DomainUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Buscamos al usuario usando el puerto de nuestro dominio
        User domainUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con el email: " + email));

        // Traducimos nuestro objeto de dominio puro al UserDetails que Spring Security exige
        return org.springframework.security.core.userdetails.User
                .withUsername(domainUser.getEmail()) 
                .password(domainUser.getPassword())  
                .authorities(domainUser.getRol().getName()) // ADMIN, USER, LEADER, etc. (sin prefijo ROLE_)
                .build();
    }
}
