package com.intelidata.acgr.pruebatecnica.infrastructure.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * 🔐 Utilidad para generar y validar tokens JWT.
 * Usa una clave secreta fija para que Spring Security pueda validar los tokens.
 */
@Component
public class JwtUtil {

    // ⚠️ Clave secreta fija (debe ser idéntica a JwtDecoderConfig)
    private static final String SECRET = "mi_clave_secreta_muy_segura_12345";

    private final SecretKey secretKey = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    // ⏱ Duración del token (1 hora)
    private static final long EXPIRATION_TIME = 1000 * 60 * 60;

    /** Genera un token JWT a partir del email del usuario */
    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    /** Valida si el token es válido */
    public boolean validateToken(String token) {
        try {
            getClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /** Extrae el email (subject) del token */
    public String extractEmail(String token) {
        return getClaims(token).getSubject();
    }

    /** Obtiene los claims decodificados del token JWT */
    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
