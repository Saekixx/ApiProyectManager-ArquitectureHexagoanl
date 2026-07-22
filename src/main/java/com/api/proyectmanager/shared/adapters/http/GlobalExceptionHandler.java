package com.api.proyectmanager.shared.adapters.http;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.api.proyectmanager.auth.domain.exceptions.InvalidCredentialsException;
import com.api.proyectmanager.shared.domain.BusinessException;
import com.api.proyectmanager.shared.domain.ValidationException;

@RestControllerAdvice // Esta anotación indica que esta clase manejará excepciones de manera global para todos los controladores REST en la aplicación.
public class GlobalExceptionHandler {
    // Manejo de Credenciales Incorrectas (Login)
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<Response<Void>> handleInvalidCredentials(InvalidCredentialsException ex) {
        // success = false, mensaje = el de la excepción, data = null
        Response<Void> response = new Response<>(false, ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    // Maneja errores lanzados dentro del constructor del DTO/Record durante la deserialización
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Response<Void>> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        String message = "El cuerpo de la solicitud no es válido.";

        // Verificamos si la causa raíz de la falla fue nuestra propia ValidationException
        if (ex.getCause() != null && ex.getCause() instanceof ValidationException) {
            message = ex.getCause().getMessage();
        } else if (ex.getRootCause() != null && ex.getRootCause() instanceof ValidationException) {
            message = ex.getRootCause().getMessage();
        }

        Response<Void> response = new Response<>(false, message, null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // Manejo de Errores de Negocio Generales
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Response<Void>> handleBusinessException(BusinessException ex) {
        Response<Void> response = new Response<>(false, ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
    
    // Manejo de Errores de Validación
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Response<Void>> handleValidationException(ValidationException ex) {
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
