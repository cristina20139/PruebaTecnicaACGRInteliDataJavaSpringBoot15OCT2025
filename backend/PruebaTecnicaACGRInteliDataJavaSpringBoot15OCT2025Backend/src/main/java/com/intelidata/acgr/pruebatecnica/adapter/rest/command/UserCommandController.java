package com.intelidata.acgr.pruebatecnica.adapter.rest.command;

import com.intelidata.acgr.pruebatecnica.application.service.CreateUserService;
import com.intelidata.acgr.pruebatecnica.domain.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 🧩 <b>Controlador REST - UserCommandController</b><br>
 *
 * Este controlador forma parte del <b>adaptador de entrada (REST Command Adapter)</b> dentro de la arquitectura
 * hexagonal o arquitectura de puertos y adaptadores. Su propósito principal es exponer un endpoint HTTP que permita
 * recibir solicitudes del mundo exterior (por ejemplo, desde un frontend o un cliente externo) y convertirlas en
 * comandos del dominio que serán procesados por la capa de aplicación. 🚀
 *
 * <p><b>📍 Rol en la arquitectura:</b><br>
 * 👉 Pertenece al <i>lado de los adaptadores de entrada</i> ("Inbound Adapters").<br>
 * 👉 Recibe solicitudes HTTP relacionadas con la creación de usuarios.<br>
 * 👉 Transforma datos JSON en objetos de dominio ({@link User}) y delega la lógica de negocio a la capa de aplicación
 * mediante el servicio {@link CreateUserService}.<br>
 * 👉 Retorna respuestas HTTP estructuradas y significativas para el cliente.
 * </p>
 *
 * <p><b>🧠 Principios SOLID aplicados:</b></p>
 * <ul>
 *   <li><b>S - Single Responsibility Principle (Responsabilidad Única):</b><br>
 *       Esta clase se encarga únicamente de orquestar la comunicación entre el cliente HTTP y el servicio de negocio.
 *       No contiene lógica de negocio ni manipula directamente la base de datos.</li>
 *
 *   <li><b>D - Dependency Inversion Principle (Inversión de Dependencias):</b><br>
 *       No depende de implementaciones concretas, sino del servicio {@link CreateUserService}, que es una abstracción
 *       de la lógica de aplicación. Esto permite desacoplar la capa REST de la capa de negocio.</li>
 * </ul>
 *
 * <p><b>⚙️ Flujo de trabajo:</b></p>
 * <ol>
 *   <li>El cliente envía una solicitud <code>POST /users</code> con un JSON que representa al usuario.</li>
 *   <li>El método {@link #createUser(User)} convierte el JSON en un objeto {@link User}.</li>
 *   <li>Se invoca el método <code>createUser()</code> del servicio {@link CreateUserService} para ejecutar la lógica de negocio.</li>
 *   <li>Si todo sale bien, retorna un mensaje de éxito; si ocurre un error, retorna una respuesta con el código HTTP apropiado.</li>
 * </ol>
 *
 * <p><b>🧱 Ejemplo de uso:</b></p>
 * <pre>
 * POST /users
 * {
 *   "id": 1,
 *   "name": "Aura Garzón",
 *   "email": "aura@example.com"
 * }
 *
 * → Respuesta exitosa:
 * {
 *   "message": "Usuario registrado exitosamente."
 * }
 * </pre>
 *
 * <p><b>🚨 Manejo de errores:</b></p>
 * <ul>
 *   <li><code>409 CONFLICT</code> → Si el correo ya existe (email duplicado).</li>
 *   <li><code>400 BAD REQUEST</code> → Si los datos son inválidos o faltantes.</li>
 *   <li><code>500 INTERNAL SERVER ERROR</code> → Si ocurre un error inesperado en el servidor.</li>
 * </ul>
 *
 * @author
 * 👩‍💻 <b>Aura Cristina Garzón Rodríguez</b>
 * @since Octubre 15, 2025
 */
@RestController
@RequestMapping("/users")
public class UserCommandController {

    private final CreateUserService createUserService;

    /**
     * 🧠 Constructor que inyecta el servicio de aplicación {@link CreateUserService},
     * encargado de manejar la lógica de negocio para la creación de usuarios.
     *
     * @param createUserService servicio que implementa la lógica de negocio de creación de usuarios.
     */
    public UserCommandController(CreateUserService createUserService) {
        this.createUserService = createUserService;
    }

    /**
     * 📨 <b>Endpoint REST para crear un nuevo usuario</b>.<br>
     * Recibe un objeto JSON con los datos del usuario, lo transforma en una entidad de dominio {@link User},
     * y delega la operación de creación al servicio {@link CreateUserService}.
     *
     * @param user objeto de dominio que representa al usuario a registrar.
     * @return una {@link ResponseEntity} con el resultado de la operación.
     *
     * <p><b>Respuestas posibles:</b></p>
     * <ul>
     *   <li>✅ <b>200 OK</b>: Usuario creado correctamente.</li>
     *   <li>⚠️ <b>409 CONFLICT</b>: El email ya está registrado.</li>
     *   <li>❌ <b>400 BAD REQUEST</b>: Datos inválidos o incompletos.</li>
     *   <li>💥 <b>500 INTERNAL SERVER ERROR</b>: Error interno inesperado.</li>
     * </ul>
     */
    @Operation(
            summary = "Crea un nuevo usuario",
            description = "Crea un nuevo usuario en el sistema",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista devuelta correctamente",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = User.class))),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
            }
    )

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try {
            createUserService.createUser(user);
            return ResponseEntity.ok(Map.of("message", "Usuario registrado exitosamente."));
        } catch (IllegalStateException ex) {
            if ("EMAIL_DUPLICATE".equals(ex.getMessage())) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(Map.of("error", "El email ya está registrado."));
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", ex.getMessage()));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error al registrar el usuario."));
        }
    }
}
