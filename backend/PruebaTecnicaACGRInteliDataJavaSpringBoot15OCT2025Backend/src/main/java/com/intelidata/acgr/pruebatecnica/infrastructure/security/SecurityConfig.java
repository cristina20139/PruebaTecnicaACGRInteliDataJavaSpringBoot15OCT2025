// com/intelidata/acgr/pruebatecnica/infrastructure/security/SecurityConfig.java
package com.intelidata.acgr.pruebatecnica.infrastructure.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * 🔐 Configuración de seguridad para la aplicación.
 *
 * Esta clase define:
 * - CSRF deshabilitado 🚫
 * - Autenticación para rutas "/users/**" 👤
 * - Sesión sin estado 🛡️
 * - Configuración de Resource Server con JWT 🏷️
 *
 * @author Aura Cristina Garzón
 * @version 1.0
 */
@Configuration
public class SecurityConfig {

    /**
     * 🔑 Define el filtro de seguridad principal de Spring Security.
     *
     * Configura:
     * 1. CSRF deshabilitado 🚫
     * 2. Autenticación requerida para "/users/**" 👤
     * 3. Sesión sin estado 🛡️
     * 4. JWT como mecanismo de validación de tokens 🏷️
     *
     * @param http instancia de {@link HttpSecurity} usada para configurar la seguridad
     * @return {@link SecurityFilterChain} cadena de filtros de seguridad configurada
     * @throws Exception si ocurre un error al construir la configuración
     */

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtDecoder jwtDecoder) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll() // login público
                        .requestMatchers("/users/**").authenticated()
                        .anyRequest().permitAll()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.decoder(jwtDecoder)) // 🔑 usa nuestro bean JwtDecoder
                );

        return http.build();
    }
}
