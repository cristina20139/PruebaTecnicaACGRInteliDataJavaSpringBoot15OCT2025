package com.intelidata.acgr.pruebatecnica.adapter.rest.query;

import com.intelidata.acgr.pruebatecnica.application.service.ListUsersService;
import com.intelidata.acgr.pruebatecnica.domain.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/**
 * 🧩 <b>Controlador REST - UserQueryController</b><br>
 *
 * Este controlador forma parte del <b>adaptador de entrada (REST Query Adapter)</b> en la arquitectura hexagonal.
 * Su propósito es permitir la <b>consulta de usuarios</b> almacenados en el sistema mediante peticiones HTTP GET.
 * 🧠 A diferencia de los comandos, que modifican el estado del sistema, este controlador se centra únicamente
 * en operaciones de lectura, respetando la separación entre <b>Command</b> y <b>Query</b> (principio CQRS). ⚖️
 *
 * <p><b>📍 Rol en la arquitectura:</b></p>
 * <ul>
 *   <li>Es un <i>adaptador de entrada</i> que expone la funcionalidad de lectura del dominio a través de un endpoint REST.</li>
 *   <li>Invoca al servicio de aplicación {@link ListUsersService}, que encapsula la lógica de recuperación de datos.</li>
 *   <li>Devuelve objetos del dominio {@link User} en formato JSON, listos para ser consumidos por un cliente externo.</li>
 * </ul>
 *
 * <p><b>🧠 Principios SOLID aplicados:</b></p>
 * <ul>
 *   <li><b>S - Single Responsibility Principle (Responsabilidad Única):</b><br>
 *       Esta clase tiene una única función: manejar las solicitudes GET y retornar la lista de usuarios.
 *       No contiene lógica de negocio ni accede directamente a la base de datos.</li>
 *
 *   <li><b>D - Dependency Inversion Principle (Inversión de Dependencias):</b><br>
 *       Depende de la abstracción {@link ListUsersService}, no de implementaciones concretas.
 *       Esto facilita el desacoplamiento y el testeo del controlador.</li>
 * </ul>
 *
 * <p><b>⚙️ Flujo de trabajo:</b></p>
 * <ol>
 *   <li>El cliente realiza una solicitud HTTP <code>GET /users</code>.</li>
 *   <li>El método {@link #listUsers()} llama al servicio {@link ListUsersService#listAll()}.</li>
 *   <li>El servicio obtiene la lista de usuarios desde la base de datos mediante los adaptadores de persistencia.</li>
 *   <li>El controlador devuelve la lista en formato JSON como respuesta.</li>
 * </ol>
 *
 * <p><b>🧱 Ejemplo de uso:</b></p>
 * <pre>
 * GET /users
 *
 * → Respuesta exitosa:
 * [
 *   {
 *     "id": 1,
 *     "name": "Aura Garzón",
 *     "email": "aura@example.com"
 *   },
 *   {
 *     "id": 2,
 *     "name": "Carlos Pérez",
 *     "email": "carlos@example.com"
 *   }
 * ]
 * </pre>
 *
 * <p><b>📊 Beneficios arquitectónicos:</b></p>
 * <ul>
 *   <li>📦 Mantiene la separación entre consultas y comandos (CQRS).</li>
 *   <li>🔍 Facilita el escalamiento de las operaciones de lectura.</li>
 *   <li>🧩 Promueve el desacoplamiento entre la capa web y la de dominio.</li>
 * </ul>
 *
 * <p><b>🚀 Buenas prácticas:</b></p>
 * <ul>
 *   <li>No realizar transformaciones de datos aquí — esto pertenece a la capa de aplicación o mappers.</li>
 *   <li>Evitar añadir lógica de negocio; su rol es solo de intermediario entre el cliente y la capa de aplicación.</li>
 * </ul>
 *
 * @author
 * 👩‍💻 <b>Aura Cristina Garzón Rodríguez</b>
 * @since Octubre 15, 2025
 */
@RestController
@RequestMapping("/users")
public class UserQueryController {

    private final ListUsersService listUsersService;

    /**
     * 🧠 Constructor que inyecta el servicio {@link ListUsersService}, responsable de obtener
     * la lista completa de usuarios desde la capa de aplicación.
     *
     * @param listUsersService servicio que gestiona las operaciones de lectura de usuarios.
     */
    public UserQueryController(ListUsersService listUsersService) {
        this.listUsersService = listUsersService;
    }

    /**
     * 📋 <b>Endpoint REST para listar todos los usuarios registrados.</b><br>
     * Atiende solicitudes HTTP GET en <code>/users</code> y devuelve una lista de objetos {@link User}.
     *
     * @return una lista con todos los usuarios del sistema.
     *
     * <p><b>Respuestas posibles:</b></p>
     * <ul>
     *   <li>✅ <b>200 OK</b>: Lista de usuarios obtenida exitosamente.</li>
     *   <li>💡 <b>Lista vacía</b>: No existen usuarios registrados.</li>
     * </ul>
     */
    @Operation(
            summary = "Listar todos los usuarios",
            description = "Devuelve la lista completa de usuarios registrados en el sistema.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista devuelta correctamente",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = User.class))),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
            }
    )
    @GetMapping
    public List<User> listUsers() {
        return listUsersService.listAll();
    }
}
