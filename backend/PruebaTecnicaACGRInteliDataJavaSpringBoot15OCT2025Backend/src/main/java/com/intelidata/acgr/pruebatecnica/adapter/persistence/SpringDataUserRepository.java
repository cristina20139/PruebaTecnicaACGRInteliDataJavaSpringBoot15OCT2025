package com.intelidata.acgr.pruebatecnica.adapter.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 * 📚 {@code SpringDataUserRepository} es una **interfaz de infraestructura**
 * que extiende {@link JpaRepository}, ofreciendo acceso a las operaciones CRUD
 * del framework **Spring Data JPA** 🧩.
 *
 * <p>Esta interfaz representa el componente más cercano a la base de datos dentro
 * de la **Arquitectura Hexagonal**, sirviendo como el mecanismo técnico que
 * ejecuta las operaciones de persistencia que el adaptador
 * {@link JpaUserRepositoryAdapter} necesita para cumplir los contratos de los puertos.</p>
 *
 * <h2>🌐 Rol en la Arquitectura</h2>
 * <ul>
 *   <li>🔸 Pertenece a la capa de <b>infraestructura</b> (adaptador de persistencia).</li>
 *   <li>🔸 Proporciona los métodos necesarios para interactuar con la base de datos usando JPA.</li>
 *   <li>🔸 Es utilizada internamente por el adaptador {@link JpaUserRepositoryAdapter},
 *       que traduce los objetos del dominio hacia y desde entidades de persistencia.</li>
 * </ul>
 *
 * <h2>🧩 Responsabilidades</h2>
 * <ul>
 *   <li>💾 Proveer operaciones básicas de persistencia (guardar, eliminar, buscar, etc.)
 *       heredadas de {@link JpaRepository}.</li>
 *   <li>🔍 Definir consultas derivadas personalizadas como:
 *       <ul>
 *         <li>{@link #existsByEmail(String)} — Verifica si existe un usuario con el correo dado.</li>
 *         <li>{@link #findByEmail(String)} — Recupera un usuario por su correo electrónico.</li>
 *       </ul>
 *   </li>
 * </ul>
 *
 * <h2>⚙️ Principios SOLID Aplicados</h2>
 * <ul>
 *   <li><b>S — Principio de Responsabilidad Única (SRP)</b> 🧩
 *       Su única tarea es definir el contrato de persistencia para la entidad {@link UserEntity}.</li>
 *
 *   <li><b>O — Principio Abierto/Cerrado (OCP)</b> 🚪
 *       Puede extenderse agregando nuevos métodos de consulta sin modificar la lógica existente.</li>
 *
 *   <li><b>I — Principio de Segregación de Interfaces (ISP)</b> ✂️
 *       La interfaz solo expone los métodos que realmente necesita el dominio o el adaptador.</li>
 *
 *   <li><b>D — Principio de Inversión de Dependencias (DIP)</b> ⚖️
 *       El adaptador {@code JpaUserRepositoryAdapter} depende de esta abstracción,
 *       no de una implementación concreta, garantizando bajo acoplamiento.</li>
 * </ul>
 *
 * <h2>🧠 Descripción Técnica</h2>
 * <p>
 * Al extender {@link JpaRepository}, esta interfaz hereda de forma automática
 * métodos como:
 * <ul>
 *   <li>{@code save(entity)} — guarda o actualiza un registro.</li>
 *   <li>{@code findById(id)} — busca por identificador.</li>
 *   <li>{@code findAll()} — recupera todos los registros.</li>
 *   <li>{@code deleteById(id)} — elimina por identificador.</li>
 * </ul>
 * Además, Spring Data JPA genera automáticamente la implementación en tiempo de ejecución 🧙‍♂️,
 * evitando la necesidad de escribir consultas SQL manualmente.
 * </p>
 *
 * <h2>📘 Ejemplo de Flujo</h2>
 * <ol>
 *   <li>El servicio de aplicación solicita guardar o consultar un usuario.</li>
 *   <li>El adaptador {@link JpaUserRepositoryAdapter} invoca los métodos de esta interfaz.</li>
 *   <li>Spring Data JPA ejecuta la consulta correspondiente contra la base de datos.</li>
 *   <li>El resultado es devuelto al adaptador, que lo traduce de entidad JPA a modelo de dominio.</li>
 * </ol>
 *
 * 🧩 En resumen, {@code SpringDataUserRepository} encapsula los detalles técnicos de JPA,
 * delegando la ejecución de las consultas al framework y manteniendo el dominio limpio,
 * desacoplado y enfocado únicamente en las reglas de negocio.
 *
 * @author  Aura Cristina Garzón Rodríguez
 * @version 1.0
 * @since   15 de octubre de 2025
 */
public interface SpringDataUserRepository extends JpaRepository<UserEntity, Long> {

    /**
     * 🔍 Verifica si existe un usuario registrado con el correo electrónico indicado.
     *
     * @param email el correo electrónico a buscar
     * @return {@code true} si el usuario existe, {@code false} en caso contrario
     */
    boolean existsByEmail(String email);

    /**
     * 📧 Busca un usuario por su dirección de correo electrónico.
     *
     * @param email el correo electrónico del usuario a buscar
     * @return un {@link Optional} que contiene la entidad del usuario si existe,
     *         o vacío si no se encontró ningún resultado
     */
    Optional<UserEntity> findByEmail(String email);
}
