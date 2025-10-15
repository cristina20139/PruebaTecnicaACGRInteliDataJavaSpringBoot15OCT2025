package com.intelidata.acgr.pruebatecnica.infrastructure.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;

/**
 * 🔐 Configuración de Spring para decodificar JWT.
 *
 * Este bean proporciona un {@link JwtDecoder} que Spring Security usará
 * para validar los tokens JWT en las rutas protegidas.
 * Se utiliza HMAC SHA-256 con una clave secreta.
 */
@Configuration
public class JwtDecoderConfig {

    /**
     * 🔑 Clave secreta para HMAC SHA-256.
     * Debe ser suficientemente larga y segura.
     */
    private static final String SECRET_KEY = "mi_clave_secreta_muy_segura_12345";

    /**
     * Bean de Spring que decodifica JWT usando la clave secreta.
     *
     * @return {@link JwtDecoder} configurado para HMAC SHA-256
     */
    @Bean
    public JwtDecoder jwtDecoder() {
        // Convertimos el String de la clave en un objeto Key compatible con NimbusJwtDecoder
        SecretKey key = new SecretKeySpec(
                SECRET_KEY.getBytes(StandardCharsets.UTF_8),
                "HmacSHA256"
        );


        // Construimos el JwtDecoder que Spring Security inyectará automáticamente
        return NimbusJwtDecoder.withSecretKey(key).build();
    }
}
