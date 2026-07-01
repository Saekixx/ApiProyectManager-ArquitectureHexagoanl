package com.api.proyectmanager.shared.adapters.http;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.api.proyectmanager.auth.domain.exceptions.InvalidCredentialsException;
import com.api.proyectmanager.shared.domain.BusinessException;

@RestControllerAdvice // Esta anotación indica que esta clase manejará excepciones de manera global para todos los controladores REST en la aplicación.
public class GlobalExceptionHandler {
    // Manejo de Credenciales Incorrectas (Login)
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<Response<Void>> handleInvalidCredentials(InvalidCredentialsException ex) {
        // success = false, mensaje = el de la excepción, data = null
        Response<Void> response = new Response<>(false, ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    // Manejo de Errores de Negocio Generales
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Response<Void>> handleBusinessException(BusinessException ex) {
        Response<Void> response = new Response<>(false, ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // Colchón de seguridad para cualquier otro error inesperado del servidor (NullPointerException, etc.)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response<Void>> handleGeneralException(Exception ex) {
        Response<Void> response = new Response<>(false, "Ocurrió un error inesperado en el sistema.", null);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
