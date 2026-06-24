package com.api.proyectmanager.user.adapters.security.config;

import com.api.proyectmanager.user.adapters.security.jwt.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    // Inyección de dependencias para JwtUtil y UserDetailsService
    private final JwtUtil jwtUtil;
    // Inyección de dependencias para UserDetailsService
    private final UserDetailsService userDetailsService;

    // Constructor para inicializar JwtUtil y UserDetailsService
    public JwtAuthenticationFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    // Método que se ejecuta para cada solicitud HTTP y verifica la autenticación JWT
    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                    HttpServletResponse response, 
                                    FilterChain filterChain) throws ServletException, IOException {
        // Obtener el encabezado de autorización de la solicitud
        final String authHeader = request.getHeader("Authorization");
        final String jwt; // Variable para almacenar el token JWT
        final String userEmail; // Variable para almacenar el correo electrónico del usuario extraído del token

        // Si el encabezado de autorización es nulo o no comienza con "Bearer ", se pasa al siguiente filtro
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extraer el token JWT del encabezado de autorización
        jwt = authHeader.substring(7);
        // Extraer el correo electrónico del usuario del token JWT usando JwtUtil
        userEmail = jwtUtil.getUsernameFromToken(jwt);

        // Si el correo electrónico del usuario no es nulo y no hay autenticación en el contexto de seguridad, se procede a autenticar al usuario
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Cargar los detalles del usuario usando UserDetailsService
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            // Validar el token JWT y, si es válido, establecer la autenticación en el contexto de seguridad
            if (jwtUtil.validateJwtToken(jwt)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
