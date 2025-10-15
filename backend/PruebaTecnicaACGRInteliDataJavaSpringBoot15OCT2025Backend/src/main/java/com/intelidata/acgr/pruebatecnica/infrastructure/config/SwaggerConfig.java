package com.intelidata.acgr.pruebatecnica.infrastructure.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import org.springframework.context.annotation.Primary;

/**
 * 🧭 <b>Configuración Swagger/OpenAPI 3 con seguridad JWT</b>
 *
 * Esta clase define la configuración completa de la documentación de la API REST,
 * incluyendo metadatos, información de contacto, licencia, enlaces a documentación externa,
 * y seguridad basada en JWT para proteger los endpoints.
 *
 * 💡 <b>Arquitectura:</b> Hexagonal (Ports and Adapters)
 * <ul>
 *   <li>🔌 Este bean actúa como un adaptador de configuración para exponer información de la API.</li>
 *   <li>🧩 La lógica de negocio NO se mezcla aquí; solo se configuran los metadatos y la seguridad.</li>
 * </ul>
 *
 * 🧠 <b>Principios SOLID aplicados:</b>
 * <ul>
 *   <li><b>S - Single Responsibility Principle:</b> Esta clase tiene una única responsabilidad: configurar OpenAPI.</li>
 *   <li><b>O - Open/Closed Principle:</b> Se puede extender la configuración agregando nuevos componentes o esquemas de seguridad sin modificar el código existente.</li>
 *   <li><b>L - Liskov Substitution Principle:</b> N/A en este contexto, pero los beans de Spring cumplen substituibilidad.</li>
 *   <li><b>I - Interface Segregation Principle:</b> N/A directamente, pero se evita sobrecargar esta clase con responsabilidades de negocio.</li>
 *   <li><b>D - Dependency Inversion Principle:</b> Esta clase depende de abstracciones (OpenAPI y sus componentes), no de implementaciones concretas de la seguridad o endpoints.</li>
 * </ul>
 *
 * 📌 <b>Detalles de la configuración:</b>
 * <ul>
 *   <li>📋 Información de la API: título, descripción, versión, contacto y licencia.</li>
 *   <li>🔗 Documentación externa: enlace a repositorio o guía completa.</li>
 *   <li>🔒 Seguridad JWT: esquema HTTP Bearer, obligatorio para proteger los endpoints.</li>
 * </ul>
 *
 * 🚀 <b>Uso:</b>
 * <pre>
 * // Bean principal para Swagger con seguridad JWT
 * @Bean
 * @Primary
 * public OpenAPI customOpenAPIWithSecurity() { ... }
 * </pre>
 *
 * Con esta configuración:
 * <ul>
 *   <li>Swagger UI mostrará la documentación completa con descripción, contacto y licencia.</li>
 *   <li>Todos los endpoints requerirán autenticación JWT usando el botón 'Authorize'.</li>
 *   <li>Se respeta la separación entre configuración y lógica de negocio (Hexagonal).</li>
 * </ul>
 *
 * 💻 <b>Autor:</b> Aura Cristina Garzón Rodríguez
 * 📅 <b>Fecha:</b> Octubre 15, 2025
 */

@Configuration
public class SwaggerConfig {



    /**
     * Crea la configuración personalizada de OpenAPI.
     *
     * @return objeto OpenAPI con los metadatos de la documentación.
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("💡 API de Gestión de Usuarios - ACGR Prueba Técnica")
                        .description("""
                                Esta API permite **registrar y consultar usuarios** dentro del sistema.
                                Incluye control de duplicados por email, validación de datos y respuestas estandarizadas.
                                ---
                                🧱 Arquitectura: Hexagonal (Ports and Adapters)
                                🚀 Framework: Spring Boot + JPA + H2
                                🧩 Propósito: Demostración de buenas prácticas de diseño
                                """)
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Aura Cristina Garzón Rodríguez")
                                .email("auracgarzonr@gmail.com")
                                .url("https://github.com/auracgarzon"))
                        .license(new License()
                                .name("Licencia MIT")
                                .url("https://opensource.org/licenses/MIT"))
                )
                .externalDocs(new ExternalDocumentation()
                        .description("Documentación del Proyecto - Guía completa 📘")
                        .url("https://github.com/auracgarzon/ACGR_PruebaTecnica"));
    }

    /**
     * 🔑 <b>OpenAPI con seguridad JWT</b>
     *
     * Este bean unifica toda la información de la API y agrega seguridad con JWT.
     * Se marca como @Primary para evitar conflictos con otros beans de OpenAPI.
     *
     * @return un objeto OpenAPI completo, listo para Swagger UI con autenticación JWT
     */


    @Bean
    @Primary
    public OpenAPI customOpenAPIWithSecurity() {
        return new OpenAPI()
                // 🔒 Configuración de seguridad JWT
                .components(new Components()
                        .addSecuritySchemes("bearerAuth",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))

                // 📝 Información completa de la API
                .info(new Info()
                        .title("💡 API de Gestión de Usuarios - ACGR Prueba Técnica")
                        .description("""
                            Esta API permite **registrar y consultar usuarios** dentro del sistema.
                            Incluye control de duplicados por email, validación de datos y respuestas estandarizadas.
                            ---
                            🧱 Arquitectura: Hexagonal (Ports and Adapters)
                            🚀 Framework: Spring Boot + JPA + H2
                            🧩 Propósito: Demostración de buenas prácticas de diseño
                            """)
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Aura Cristina Garzón Rodríguez")
                                .email("auracgarzonr@gmail.com")
                                .url("https://github.com/auracgarzon"))
                        .license(new License()
                                .name("Licencia MIT")
                                .url("https://opensource.org/licenses/MIT")))

                // 🌐 Documentación externa
                .externalDocs(new ExternalDocumentation()
                        .description("Documentación del Proyecto - Guía completa 📘")
                        .url("https://github.com/auracgarzon/ACGR_PruebaTecnica"));
    }
}
