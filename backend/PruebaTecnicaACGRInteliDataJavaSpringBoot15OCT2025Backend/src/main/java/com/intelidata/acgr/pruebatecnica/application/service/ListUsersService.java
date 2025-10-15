package com.intelidata.acgr.pruebatecnica.application.service;

import com.intelidata.acgr.pruebatecnica.application.port.output.UserReadRepositoryPort;
import com.intelidata.acgr.pruebatecnica.domain.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 🚀 <b>Servicio de Aplicación — ListUsersService</b><br>
 *
 * Esta clase representa el <b>servicio de aplicación encargado de listar todos los usuarios</b>
 * del sistema dentro de la arquitectura hexagonal (Ports and Adapters).
 *
 * Su función principal es <b>coordinar la recuperación de datos</b> desde el puerto de salida
 * {@link UserReadRepositoryPort}, garantizando que la capa de aplicación permanezca
 * desacoplada de los detalles de infraestructura o persistencia. 📦
 *
 * <p><b>📍 Rol en la arquitectura:</b></p>
 * <ul>
 *   <li>Forma parte de la <b>Capa de Aplicación</b> (Application Layer), que define los casos de uso del dominio.</li>
 *   <li>Actúa como intermediario entre los adaptadores de entrada (por ejemplo, controladores REST)
 *       y los puertos de salida encargados de acceder a los datos.</li>
 *   <li>No contiene lógica de negocio compleja; su única responsabilidad es obtener y devolver
 *       la lista de usuarios.</li>
 * </ul>
 *
 * <p><b>🧠 Principios SOLID aplicados:</b></p>
 * <ul>
 *   <li><b>SRP (Single Responsibility Principle):</b> esta clase se encarga exclusivamente del caso de uso
 *       “listar usuarios”. No realiza validaciones ni transformaciones de datos innecesarias.</li>
 *   <li><b>DIP (Dependency Inversion Principle):</b> depende de la abstracción {@link UserReadRepositoryPort}
 *       y no de una implementación concreta de persistencia (como JPA o JDBC), lo que la hace fácilmente
 *       extensible y testeable. 🧩</li>
 * </ul>
 *
 * <p><b>🧱 Flujo lógico del caso de uso:</b></p>
 * <ol>
 *   <li>📥 El servicio recibe una solicitud desde un adaptador de entrada, como {@code UserQueryController}.</li>
 *   <li>🔍 Invoca el método {@link UserReadRepositoryPort#findAll()} para recuperar todos los usuarios almacenados.</li>
 *   <li>📤 Retorna la lista de objetos {@link User} al controlador, que la enviará al cliente o consumidor de la API.</li>
 * </ol>
 *
 * <p><b>📊 Beneficios de diseño:</b></p>
 * <ul>
 *   <li>🧩 Desacopla la lógica de la aplicación de los mecanismos de persistencia.</li>
 *   <li>🧪 Facilita las pruebas unitarias (se puede simular el puerto de lectura con mocks).</li>
 *   <li>🔄 Promueve la mantenibilidad y escalabilidad del sistema.</li>
 * </ul>
 *
 * <p><b>🧱 Ejemplo de uso en un controlador REST:</b></p>
 * <pre>{@code
 * @RestController
 * @RequestMapping("/users")
 * public class UserQueryController {
 *
 *     private final ListUsersService listUsersService;
 *
 *     public UserQueryController(ListUsersService listUsersService) {
 *         this.listUsersService = listUsersService;
 *     }
 *
 *     @GetMapping
 *     public List<User> listUsers() {
 *         return listUsersService.listAll();
 *     }
 * }
 * }</pre>
 *
 * <p><b>🧪 Ejemplo de prueba unitaria:</b></p>
 * <pre>{@code
 * @Test
 * void shouldReturnAllUsers() {
 *     List<User> users = List.of(new User(1L, "Aura", "aura@mail.com"));
 *     when(readPort.findAll()).thenReturn(users);
 *
 *     List<User> result = listUsersService.listAll();
 *
 *     assertEquals(1, result.size());
 *     assertEquals("Aura", result.get(0).getName());
 * }
 * }</pre>
 *
 * <p><b>📘 Anotaciones:</b></p>
 * <ul>
 *   <li>{@link Service}: Indica que esta clase es un componente de servicio gestionado por Spring.</li>
 * </ul>
 *
 * <p><b>💬 Notas adicionales:</b></p>
 * <ul>
 *   <li>El servicio no aplica filtros ni ordenamientos; simplemente delega al puerto la obtención de los datos.</li>
 *   <li>Puede extenderse en el futuro para soportar paginación, búsqueda por criterios o filtrado avanzado. 🔍</li>
 * </ul>
 *
 * @author
 * 👩‍💻 <b>Aura Cristina Garzón Rodríguez</b>
 * @since Octubre 15, 2025
 */
@Service
public class ListUsersService {

    /**
     * Puerto de salida responsable de las operaciones de lectura sobre usuarios.
     * Se inyecta mediante el constructor gracias a la inversión de control de Spring (IoC).
     */
    private final UserReadRepositoryPort readPort;

    /**
     * 🔧 Constructor que inyecta la dependencia del puerto de lectura.
     *
     * @param readPort implementación concreta del puerto {@link UserReadRepositoryPort}.
     */
    public ListUsersService(UserReadRepositoryPort readPort) {
        this.readPort = readPort;
    }

    /**
     * 📋 <b>Lista todos los usuarios</b> disponibles en el sistema.
     *
     * @return una lista de objetos {@link User}.
     */
    public List<User> listAll() {
        return readPort.findAll();
    }
}
