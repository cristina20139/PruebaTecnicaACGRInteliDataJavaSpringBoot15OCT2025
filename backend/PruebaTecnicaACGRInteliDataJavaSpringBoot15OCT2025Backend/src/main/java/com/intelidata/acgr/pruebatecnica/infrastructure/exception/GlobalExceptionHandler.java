package com.intelidata.acgr.pruebatecnica.infrastructure.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

/**
 * Clase que gestiona de manera global las excepciones que pueden ocurrir
 * en cualquier controlador de la aplicación.
 *
 * Su propósito es centralizar la lógica de manejo de errores,
 * garantizando respuestas coherentes y significativas al cliente.
 */
@ControllerAdvice // Permite que esta clase intercepte excepciones lanzadas por cualquier @RestController
public class GlobalExceptionHandler {

    /**
     * Maneja excepciones del tipo IllegalStateException.
     * Este tipo de error se lanza cuando el estado de un objeto o proceso no es válido.
     *
     * @param ex la excepción capturada
     * @return una respuesta HTTP con el código y mensaje de error correspondiente
     */
    @ExceptionHandler(IllegalStateException.class) // Indica a Spring que este método maneja IllegalStateException
    public ResponseEntity<?> handleIllegalState(IllegalStateException ex) {
        // Si el mensaje de la excepción es "EMAIL_DUPLICATE", se asume que ya existe un usuario con ese correo
        if ("EMAIL_DUPLICATE".equals(ex.getMessage())) {
            // Devuelve una respuesta con código HTTP 409 (Conflict) y un mensaje descriptivo
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("error", "El email ya está registrado."));
        }
        // Para cualquier otro mensaje, se devuelve un error genérico con estado 400 (Bad Request)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", ex.getMessage()));
    }

    /**
     * Maneja excepciones del tipo IllegalArgumentException.
     * Este tipo de excepción se lanza cuando un argumento pasado a un método no es válido.
     *
     * @param ex la excepción capturada
     * @return una respuesta con código HTTP 400 (Bad Request) y el mensaje del error.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleBadRequest(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", ex.getMessage()));
    }

    /**
     * Maneja cualquier otra excepción no controlada por los manejadores anteriores.
     * Esta es una "red de seguridad" para capturar errores inesperados en el sistema.
     *
     * @param ex la excepción genérica capturada
     * @return una respuesta con código HTTP 500 (Internal Server Error) y un mensaje genérico.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleOther(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Error interno"));
    }
}
