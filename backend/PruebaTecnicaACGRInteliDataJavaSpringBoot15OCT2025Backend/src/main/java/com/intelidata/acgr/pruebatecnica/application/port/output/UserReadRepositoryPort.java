package com.intelidata.acgr.pruebatecnica.application.port.output;

import com.intelidata.acgr.pruebatecnica.domain.model.User;
import java.util.List;

/**
 * 🧩 <b>Puerto de salida — UserReadRepositoryPort</b><br>
 *
 * Esta interfaz define el contrato de lectura para el acceso a los datos de usuarios dentro de la
 * <b>arquitectura hexagonal (Ports and Adapters)</b>.
 * Su función principal es permitir que la capa de aplicación obtenga información del dominio
 * sin depender de detalles de infraestructura como JPA, SQL o cualquier motor de base de datos específico. 🧠
 *
 * <p><b>📍 Rol en la arquitectura:</b></p>
 * <ul>
 *   <li>Forma parte de los <b>puertos de salida</b> (outbound ports), que establecen las reglas mediante las cuales
 *       la capa de aplicación se comunica con el mundo exterior (bases de datos, APIs externas, etc.).</li>
 *   <li>Su implementación concreta (por ejemplo, {@code JpaUserRepositoryAdapter}) actúa como un <b>adaptador de salida</b>,
 *       conectando la lógica del dominio con la tecnología de persistencia elegida.</li>
 *   <li>Permite listar todos los usuarios del sistema a través del método {@link #findAll()}.</li>
 * </ul>
 *
 * <p><b>🧠 Principios SOLID aplicados:</b></p>
 * <ul>
 *   <li><b>I - Interface Segregation Principle (Segregación de Interfaces):</b><br>
 *       Define una interfaz pequeña y específica, enfocada solo en las operaciones de lectura.
 *       De esta forma, las clases que implementan este contrato no se ven obligadas a incluir métodos de escritura.</li>
 *
 *   <li><b>D - Dependency Inversion Principle (Inversión de Dependencias):</b><br>
 *       La capa de aplicación depende de esta <i>abstracción</i>, no de una clase concreta de persistencia.
 *       Esto permite reemplazar la tecnología de base de datos (por ejemplo, cambiar de JPA a MongoDB o a un servicio REST)
 *       sin alterar la lógica del dominio. 🔄</li>
 * </ul>
 *
 * <p><b>⚙️ Flujo de uso típico:</b></p>
 * <ol>
 *   <li>Un servicio de aplicación, como {@code ListUsersService}, invoca este puerto para obtener los usuarios.</li>
 *   <li>El puerto es implementado por un adaptador concreto, por ejemplo {@code JpaUserRepositoryAdapter}.</li>
 *   <li>El adaptador accede a la base de datos, recupera los registros y los transforma en objetos {@link User} del dominio.</li>
 *   <li>La lista de usuarios se devuelve a la capa de aplicación y finalmente al controlador REST.</li>
 * </ol>
 *
 * <p><b>📊 Beneficios arquitectónicos:</b></p>
 * <ul>
 *   <li>🧩 Desacopla la capa de dominio de la infraestructura.</li>
 *   <li>🔍 Facilita las pruebas unitarias y la sustitución de implementaciones.</li>
 *   <li>⚙️ Permite cambiar la tecnología de persistencia sin modificar la lógica del negocio.</li>
 * </ul>
 *
 * <p><b>🧱 Ejemplo conceptual:</b></p>
 * <pre>
 * // En la capa de aplicación
 * List<User> users = userReadRepositoryPort.findAll();
 * </pre>
 *
 * <p><b>🚀 Buenas prácticas:</b></p>
 * <ul>
 *   <li>Evitar incluir métodos que no sean de lectura en esta interfaz — mantener la separación clara entre lectura y escritura (CQRS).</li>
 *   <li>Nombrar los puertos de forma coherente con su propósito: “Read”, “Write”, “Notification”, etc.</li>
 * </ul>
 *
 * @author
 * 👩‍💻 <b>Aura Cristina Garzón Rodríguez</b>
 * @since Octubre 15, 2025
 */
public interface UserReadRepositoryPort {

    /**
     * 📋 <b>Obtiene todos los usuarios registrados en el sistema.</b><br>
     * Este método se encarga de recuperar la lista completa de objetos {@link User} desde la fuente de datos.
     *
     * @return una lista de usuarios del dominio.
     */
    List<User> findAll();
}
