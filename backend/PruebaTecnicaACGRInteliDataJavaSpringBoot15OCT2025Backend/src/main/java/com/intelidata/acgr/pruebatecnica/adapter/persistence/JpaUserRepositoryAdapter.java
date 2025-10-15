package com.intelidata.acgr.pruebatecnica.adapter.persistence;

import com.intelidata.acgr.pruebatecnica.application.port.output.UserReadRepositoryPort;
import com.intelidata.acgr.pruebatecnica.application.port.output.UserWriteRepositoryPort;
import com.intelidata.acgr.pruebatecnica.domain.model.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 📦 {@code JpaUserRepositoryAdapter} actúa como el **Adaptador de Persistencia**
 * dentro de la **Arquitectura Hexagonal (Puertos y Adaptadores)** 🧩.
 *
 * <p>Esta clase funciona como un puente 🔗 entre la **capa de dominio** y la **capa de infraestructura**,
 * permitiendo que la lógica de negocio (el dominio) permanezca completamente independiente
 * de la tecnología de persistencia (como JPA o cualquier base de datos específica).
 *
 * <h2>🌐 Rol en la Arquitectura</h2>
 * <ul>
 *   <li>Implementa los puertos de salida definidos por la aplicación:
 *       <ul>
 *         <li>{@link com.intelidata.acgr.pruebatecnica.application.port.output.UserReadRepositoryPort}</li>
 *         <li>{@link com.intelidata.acgr.pruebatecnica.application.port.output.UserWriteRepositoryPort}</li>
 *       </ul>
 *   </li>
 *   <li>Delegar las operaciones de acceso a datos al repositorio {@link SpringDataUserRepository} ⚙️.</li>
 *   <li>Convertir los modelos de dominio ({@link com.intelidata.acgr.pruebatecnica.domain.model.User})
 *       en entidades de persistencia ({@link UserEntity}) y viceversa.</li>
 * </ul>
 *
 * <h2>🎯 Responsabilidades Principales</h2>
 * <ul>
 *   <li>💾 Guardar usuarios en la base de datos mediante JPA.</li>
 *   <li>🔍 Verificar si un usuario existe a partir de su correo electrónico.</li>
 *   <li>📋 Consultar y devolver todos los usuarios almacenados en el sistema.</li>
 * </ul>
 *
 * <h2>🧱 Relación con los Principios SOLID</h2>
 * <ul>
 *   <li><b>S — Principio de Responsabilidad Única (SRP)</b> 🧩
 *       Esta clase tiene una única responsabilidad: servir como mediador entre el dominio
 *       y la capa de persistencia. No contiene lógica de negocio ni configuración de base de datos.</li>
 *
 *   <li><b>O — Principio Abierto/Cerrado (OCP)</b> 🚪
 *       Se puede extender para utilizar otro mecanismo de persistencia (por ejemplo MongoDB, JDBC o Redis)
 *       sin modificar el código del dominio o de la aplicación.</li>
 *
 *   <li><b>L — Principio de Sustitución de Liskov (LSP)</b> 🔄
 *       Cualquier otra clase que implemente los mismos puertos puede sustituir a este adaptador
 *       sin afectar el comportamiento del sistema.</li>
 *
 *   <li><b>I — Principio de Segregación de Interfaces (ISP)</b> ✂️
 *       Esta clase depende únicamente de las interfaces específicas que necesita
 *       (`UserReadRepositoryPort` y `UserWriteRepositoryPort`), sin verse obligada
 *       a implementar métodos innecesarios.</li>
 *
 *   <li><b>D — Principio de Inversión de Dependencias (DIP)</b> ⚖️
 *       La capa de dominio no depende de esta implementación concreta,
 *       sino de las abstracciones (puertos), manteniendo un bajo acoplamiento.</li>
 * </ul>
 *
 * <h2>🧭 Resumen General</h2>
 * <p>
 * ✅ Separación clara de responsabilidades.<br>
 * ✅ Independencia tecnológica respecto al framework o la base de datos.<br>
 * ✅ Facilita el mantenimiento y la evolución del sistema.<br>
 * ✅ Perfecta adherencia a la filosofía de la Arquitectura Hexagonal.<br>
 * </p>
 *
 * <h2>📘 Ejemplo de Flujo</h2>
 * <ol>
 *   <li>El servicio de aplicación solicita guardar o consultar un usuario a través de un puerto.</li>
 *   <li>El puerto es implementado por esta clase adaptadora.</li>
 *   <li>La clase transforma el modelo de dominio en entidad JPA y delega la operación a {@code SpringDataUserRepository}.</li>
 *   <li>Finalmente, traduce los resultados de vuelta al modelo de dominio antes de devolverlos al servicio.</li>
 * </ol>
 *
 * 🧠 En resumen, este adaptador traduce el lenguaje del dominio al lenguaje de la base de datos,
 * manteniendo ambos mundos completamente desacoplados.
 *
 * @author  Aura Cristina Garzón Rodríguez
 * @version 1.0
 * @since   15 de octubre de 2025
 */
@Component
public class JpaUserRepositoryAdapter implements UserReadRepositoryPort, UserWriteRepositoryPort {

    /**
     * Repositorio Spring Data JPA interno utilizado para acceder a la base de datos 🗃️.
     */
    private final SpringDataUserRepository springDataUserRepository;

    /**
     * 💡 Constructor del adaptador que recibe e inyecta el repositorio JPA.
     *
     * @param springDataUserRepository el repositorio encargado de las operaciones CRUD reales
     */
    public JpaUserRepositoryAdapter(SpringDataUserRepository springDataUserRepository) {
        this.springDataUserRepository = springDataUserRepository;
    }

    /**
     * 💾 Guarda un {@link User} en la base de datos.
     * Convierte el modelo de dominio en una entidad JPA antes de persistirlo.
     *
     * @param user el usuario que se desea almacenar
     */
    @Override
    public void save(User user) {
        UserEntity e = new UserEntity(user.getId(), user.getName(), user.getEmail());
        springDataUserRepository.save(e);
    }

    /**
     * 🔍 Verifica si existe un usuario registrado con el correo electrónico indicado.
     *
     * @param email correo electrónico del usuario a buscar
     * @return {@code true} si el usuario existe, {@code false} en caso contrario
     */
    @Override
    public boolean existsByEmail(String email) {
        return springDataUserRepository.existsByEmail(email);
    }

    /**
     * 📋 Recupera todos los usuarios almacenados en la base de datos y los
     * convierte en objetos de dominio {@link User}.
     *
     * @return lista de todos los usuarios disponibles en el sistema
     */
    @Override
    public List<User> findAll() {
        return springDataUserRepository.findAll()
                .stream()
                .map(e -> new User(e.getId(), e.getName(), e.getEmail()))
                .collect(Collectors.toList());
    }
}
