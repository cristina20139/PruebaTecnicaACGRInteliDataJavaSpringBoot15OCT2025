package com.intelidata.acgr.pruebatecnica.application.port.output;

import com.intelidata.acgr.pruebatecnica.domain.model.User;

/**
 * 🧩 <b>Puerto de salida — UserWriteRepositoryPort</b><br>
 *
 * Esta interfaz representa el <b>contrato de escritura</b> del repositorio de usuarios dentro de la
 * <b>arquitectura hexagonal (Ports and Adapters)</b>.
 * Define las operaciones necesarias para registrar o validar la existencia de usuarios
 * en la fuente de datos persistente (base de datos, memoria, API externa, etc.). 💾
 *
 * <p><b>📍 Rol en la arquitectura:</b></p>
 * <ul>
 *   <li>Forma parte de los <b>puertos de salida</b> (outbound ports), los cuales permiten que la capa de aplicación
 *       interactúe con la infraestructura sin depender de su implementación concreta.</li>
 *   <li>Su implementación, como {@code JpaUserRepositoryAdapter}, actúa como un <b>adaptador de salida</b>
 *       que traduce las operaciones del dominio a comandos de persistencia (por ejemplo, JPA o JDBC).</li>
 *   <li>Complementa a {@link UserReadRepositoryPort}, separando las responsabilidades de lectura y escritura según el patrón <b>CQRS</b>.</li>
 * </ul>
 *
 * <p><b>🧠 Principios SOLID aplicados:</b></p>
 * <ul>
 *   <li><b>I - Interface Segregation Principle (Segregación de Interfaces):</b><br>
 *       Esta interfaz solo contiene las operaciones de escritura y verificación,
 *       evitando sobrecargarla con responsabilidades de lectura o consultas complejas.</li>
 *
 *   <li><b>D - Dependency Inversion Principle (Inversión de Dependencias):</b><br>
 *       Los servicios de aplicación, como {@code CreateUserService}, dependen de esta abstracción,
 *       no de una clase concreta de persistencia.
 *       Esto permite cambiar de tecnología (JPA, MongoDB, DynamoDB, etc.) sin afectar la lógica del negocio. 🔄</li>
 * </ul>
 *
 * <p><b>⚙️ Flujo de uso típico:</b></p>
 * <ol>
 *   <li>Un servicio de aplicación (por ejemplo, {@code CreateUserService}) recibe una solicitud para crear un nuevo usuario.</li>
 *   <li>Antes de guardar, verifica si el correo electrónico ya existe usando {@link #existsByEmail(String)}.</li>
 *   <li>Si no hay duplicados, invoca {@link #save(User)} para registrar al nuevo usuario en la fuente de datos.</li>
 *   <li>La implementación concreta del puerto maneja los detalles de persistencia (ORM, SQL, etc.).</li>
 * </ol>
 *
 * <p><b>📊 Beneficios arquitectónicos:</b></p>
 * <ul>
 *   <li>🧩 Desacopla la lógica de negocio del mecanismo de persistencia.</li>
 *   <li>🔒 Aísla las validaciones de duplicidad en un punto central.</li>
 *   <li>🧪 Facilita la prueba unitaria del dominio usando implementaciones "mock" o "in-memory".</li>
 * </ul>
 *
 * <p><b>🧱 Ejemplo conceptual:</b></p>
 * <pre>
 * if (!userWriteRepositoryPort.existsByEmail(user.getEmail())) {
 *     userWriteRepositoryPort.save(user);
 * } else {
 *     throw new IllegalStateException("EMAIL_DUPLICATE");
 * }
 * </pre>
 *
 * <p><b>🚀 Buenas prácticas:</b></p>
 * <ul>
 *   <li>Separar las operaciones de lectura y escritura en diferentes interfaces.</li>
 *   <li>Nombrar los métodos de manera semántica, representando claramente su intención.</li>
 *   <li>Delegar las validaciones de unicidad (como el correo electrónico) al dominio o a la capa de persistencia.</li>
 * </ul>
 *
 * @author
 * 👩‍💻 <b>Aura Cristina Garzón Rodríguez</b>
 * @since Octubre 15, 2025
 */
public interface UserWriteRepositoryPort {

    /**
     * 💾 <b>Guarda un nuevo usuario en la fuente de datos.</b><br>
     * Este método persiste un objeto {@link User} en el sistema, ya sea en memoria o en una base de datos relacional.
     *
     * @param user el objeto {@link User} que se desea registrar.
     */
    void save(User user);

    /**
     * 🔍 <b>Verifica si un correo electrónico ya existe en el sistema.</b><br>
     * Previene la duplicidad de usuarios basándose en el campo {@code email}.
     *
     * @param email el correo electrónico a verificar.
     * @return {@code true} si el correo ya está registrado, {@code false} en caso contrario.
     */
    boolean existsByEmail(String email);
}
