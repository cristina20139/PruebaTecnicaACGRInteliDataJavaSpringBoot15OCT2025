package com.intelidata.acgr.pruebatecnica.adapter.rest.auth;

import com.intelidata.acgr.pruebatecnica.infrastructure.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Controlador para autenticación y generación de tokens JWT.
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtUtil jwtUtil;

    public AuthController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String email) {
        // En un caso real, aquí validarías usuario y contraseña en base de datos
        String token = jwtUtil.generateToken(email);
        return ResponseEntity.ok(Map.of(
                "token", token,
                "message", "Token generado correctamente para " + email
        ));
    }
}
