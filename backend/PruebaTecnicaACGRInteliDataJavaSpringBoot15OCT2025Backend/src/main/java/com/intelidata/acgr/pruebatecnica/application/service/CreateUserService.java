// com/intelidata/acgr/pruebatecnica/application/service/CreateUserService.java
package com.intelidata.acgr.pruebatecnica.application.service;

import com.intelidata.acgr.pruebatecnica.application.port.output.UserWriteRepositoryPort;
import com.intelidata.acgr.pruebatecnica.domain.model.User;
import org.springframework.stereotype.Service;

/**
 * 🚀 <b>Servicio de Aplicación — CreateUserService</b><br>
 *
 * Esta clase representa el <b>servicio de aplicación encargado de la creación de usuarios</b>
 * dentro de la arquitectura hexagonal (Ports and Adapters).
 *
 * Su función es coordinar la lógica necesaria para registrar un nuevo usuario, asegurando la
 * validez del correo electrónico y evitando duplicidades antes de delegar la persistencia
 * al puerto de salida {@link UserWriteRepositoryPort}. 💡
 *
 * <p><b>📍 Rol en la arquitectura:</b></p>
 * <ul>
 *   <li>Forma parte de la capa de <b>Aplicación</b> (Application Layer), la cual orquesta los casos de uso del dominio.</li>
 *   <li>No contiene lógica de infraestructura ni de persistencia: en lugar de ello, delega dichas operaciones
 *       al puerto de salida {@code UserWriteRepositoryPort}.</li>
 *   <li>Interactúa con el modelo del dominio {@link User}, asegurando que se respeten las reglas básicas del negocio
 *       (por ejemplo, no permitir correos vacíos o duplicados).</li>
 * </ul>
 *
 * <p><b>🧠 Principios aplicados:</b></p>
 * <ul>
 *   <li><b>SRP (Single Responsibility Principle):</b> esta clase tiene una única responsabilidad:
 *       crear usuarios de manera controlada, verificando reglas básicas de negocio.</li>
 *   <li><b>DIP (Dependency Inversion Principle):</b> depende de la abstracción {@link UserWriteRepositoryPort}
 *       y no de una implementación concreta (como JPA o JDBC), favoreciendo la extensibilidad. 🧩</li>
 * </ul>
 *
 * <p><b>🧱 Flujo lógico del caso de uso:</b></p>
 * <ol>
 *   <li>📥 El servicio recibe un objeto {@link User} desde el adaptador de entrada (por ejemplo, un controlador REST).</li>
 *   <li>✅ Valida que el campo <code>email</code> no sea nulo ni vacío.</li>
 *   <li>🔍 Verifica si el correo ya existe mediante {@link UserWriteRepositoryPort#existsByEmail(String)}.</li>
 *   <li>💾 Si no existe, invoca {@link UserWriteRepositoryPort#save(User)} para persistir el nuevo usuario.</li>
 *   <li>⚠️ Si el correo ya está registrado, lanza una excepción <code>IllegalStateException</code> con el mensaje <b>"EMAIL_DUPLICATE"</b>.</li>
 * </ol>
 *
 * <p><b>📊 Beneficios de diseño:</b></p>
 * <ul>
 *   <li>🧩 Desacopla la lógica de negocio de la persistencia.</li>
 *   <li>🧪 Facilita la prueba unitaria (mock del puerto de escritura).</li>
 *   <li>♻️ Permite intercambiar fácilmente la tecnología de almacenamiento sin modificar el caso de uso.</li>
 * </ul>
 *
 * <p><b>🧱 Ejemplo de uso en un controlador REST:</b></p>
 * <pre>{@code
 * @RestController
 * @RequestMapping("/users")
 * public class UserController {
 *
 *     private final CreateUserService createUserService;
 *
 *     public UserController(CreateUserService createUserService) {
 *         this.createUserService = createUserService;
 *     }
 *
 *     @PostMapping
 *     public ResponseEntity<String> createUser(@RequestBody User user) {
 *         createUserService.createUser(user);
 *         return ResponseEntity.ok("Usuario creado exitosamente");
 *     }
 * }
 * }</pre>
 *
 * <p><b>🚨 Excepciones manejadas:</b></p>
 * <ul>
 *   <li>{@link IllegalArgumentException}: Si el campo <code>email</code> es nulo o está vacío.</li>
 *   <li>{@link IllegalStateException}: Si ya existe un usuario con el mismo correo electrónico.</li>
 * </ul>
 *
 * <p><b>🧪 Ejemplo de prueba unitaria:</b></p>
 * <pre>{@code
 * @Test
 * void shouldThrowExceptionWhenEmailAlreadyExists() {
 *     when(writePort.existsByEmail("test@mail.com")).thenReturn(true);
 *
 *     assertThrows(IllegalStateException.class,
 *         () -> createUserService.createUser(new User("test@mail.com")));
 * }
 * }</pre>
 *
 * <p><b>📘 Anotaciones:</b></p>
 * <ul>
 *   <li>{@link Service}: indica a Spring que esta clase es un componente de servicio gestionado por el contenedor de IoC.</li>
 * </ul>
 *
 * @author
 * 👩‍💻 <b>Aura Cristina Garzón Rodríguez</b>
 * @since Octubre 15, 2025
 */
@Service
public class CreateUserService {

    /**
     * Puerto de salida responsable de las operaciones de escritura de usuarios.
     * Inyectado mediante el constructor gracias a Spring (Inversión de Control - IoC).
     */
    private final UserWriteRepositoryPort writePort;

    /**
     * 🔧 Constructor que permite la inyección del puerto de escritura.
     *
     * @param writePort implementación concreta del puerto {@link UserWriteRepositoryPort}.
     */
    public CreateUserService(UserWriteRepositoryPort writePort) {
        this.writePort = writePort;
    }

    /**
     * ✨ <b>Crea un nuevo usuario</b> en el sistema, validando que el correo electrónico
     * no sea nulo, vacío ni duplicado.
     *
     * @param user el objeto {@link User} que se desea registrar.
     * @throws IllegalArgumentException si el correo está vacío o nulo.
     * @throws IllegalStateException si ya existe un usuario con el mismo correo electrónico.
     */
    public void createUser(User user) {
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email no puede ser vacío");
        }

        if (writePort.existsByEmail(user.getEmail())) {
            throw new IllegalStateException("EMAIL_DUPLICATE");
        }

        writePort.save(user);
    }
}
