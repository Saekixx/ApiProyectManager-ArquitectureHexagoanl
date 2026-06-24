package com.api.proyectmanager.auth.domain.exceptions;

/**
 * Excepción de dominio que se lanza cuando el correo o la 
 * contraseña no coinciden con los registros del sistema.
 */
public class InvalidCredentialsException extends RuntimeException {
    
    public InvalidCredentialsException(String message) {
        super(message);
    }
}
