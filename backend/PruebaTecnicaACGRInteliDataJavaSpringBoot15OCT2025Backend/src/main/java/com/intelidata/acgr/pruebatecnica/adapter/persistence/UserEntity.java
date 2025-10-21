// com/intelidata/acgr/pruebatecnica/adapter/persistence/UserEntity.java
package com.intelidata.acgr.pruebatecnica.adapter.persistence;

import jakarta.persistence.*;

/**
 * 🧱 {@code UserEntity} representa la **entidad de persistencia** asociada
 * a la tabla <b>users</b> en la base de datos.
 * Es el modelo que Spring Data JPA utiliza para mapear los registros de la tabla
 * hacia objetos Java y viceversa.
 *
 * <p>Esta clase actúa como el puente técnico entre el **modelo de dominio**
 * {@link com.intelidata.acgr.pruebatecnica.domain.model.User} y la base de datos
 * relacional. Es gestionada automáticamente por el contexto de persistencia de JPA.</p>
 *
 * <h2>🌐 Rol en la Arquitectura Hexagonal</h2>
 * <ul>
 *   <li>🏗️ Pertenece a la **capa de infraestructura**, específicamente al adaptador de persistencia.</li>
 *   <li>🔸 Define cómo los datos del dominio se almacenan físicamente en la base de datos.</li>
 *   <li>🔸 Es utilizada por {@link SpringDataUserRepository} y
 *       {@link JpaUserRepositoryAdapter} para realizar operaciones CRUD.</li>
 *   <li>🔸 Permite desacoplar el dominio de los detalles técnicos de JPA o SQL.</li>
 * </ul>
 *
 * <h2>🧩 Mapeo JPA</h2>
 * <ul>
 *   <li>{@link Entity} — Indica que esta clase es una entidad gestionada por JPA.</li>
 *   <li>{@link Table} — Especifica el nombre de la tabla <b>users</b> y define una restricción
 *       única sobre la columna <b>email</b>.</li>
 *   <li>{@link Id} — Marca el campo <b>id</b> como clave primaria.</li>
 *   <li>{@link Column} — Configura propiedades de las columnas, como
 *       <b>nullable = false</b> o <b>unique = true</b>.</li>
 * </ul>
 *
 * <h2>⚙️ Principios SOLID Aplicados</h2>
 * <ul>
 *   <li><b>S — Principio de Responsabilidad Única (SRP)</b> 🧩
 *       Su única función es representar la estructura de la tabla <b>users</b> y su mapeo con JPA.</li>
 *
 *   <li><b>O — Principio Abierto/Cerrado (OCP)</b> 🚪
 *       Puede extenderse con nuevas columnas o relaciones sin alterar el código base que ya funciona.</li>
 *
 *   <li><b>L — Principio de Sustitución de Liskov (LSP)</b> 🔄
 *       Puede ser usada en cualquier contexto donde se espere una entidad base de JPA sin romper funcionalidad.</li>
 *
 *   <li><b>D — Principio de Inversión de Dependencias (DIP)</b> ⚖️
 *       El dominio no depende directamente de esta clase; la dependencia se inyecta a través de los puertos,
 *       manteniendo el desacoplamiento entre el núcleo de negocio y la infraestructura.</li>
 * </ul>
 *
 * <h2>📘 Campos de la Entidad</h2>
 * <ul>
 *   <li><b>id</b> — Identificador único del usuario. Actúa como clave primaria.</li>
 *   <li><b>name</b> — Nombre del usuario. Campo obligatorio.</li>
 *   <li><b>email</b> — Correo electrónico único. Campo obligatorio con restricción de unicidad.</li>
 * </ul>
 *
 * <h2>🧠 Descripción Técnica</h2>
 * <p>
 * Spring Data JPA gestiona automáticamente la creación de la tabla y la persistencia
 * de los objetos {@code UserEntity}. Cada instancia representa una fila dentro de
 * la tabla <b>users</b>.
 * El uso de anotaciones de JPA evita tener que escribir manualmente sentencias SQL,
 * permitiendo un enfoque declarativo y mantenible.
 * </p>
 *
 * <h2>⚡ Ejemplo de Flujo</h2>
 * <ol>
 *   <li>El dominio crea un objeto {@code User} con nombre y correo.</li>
 *   <li>El adaptador {@link JpaUserRepositoryAdapter} transforma ese objeto
 *       en una instancia de {@code UserEntity}.</li>
 *   <li>El repositorio {@link SpringDataUserRepository} lo guarda en la base de datos.</li>
 *   <li>Cuando se consulta, la entidad es recuperada, convertida nuevamente a
 *       {@code User} y devuelta al dominio.</li>
 * </ol>
 *
 * <h2>🏁 Conclusión</h2>
 * {@code UserEntity} es una pieza esencial de la capa de persistencia,
 * garantizando que el modelo de negocio permanezca **puro y desacoplado**
 * de los detalles técnicos de la base de datos 💾.
 *
 * @author  Aura Cristina Garzón Rodríguez
 * @version 1.0
 * @since   15 de octubre de 2025
 */
@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class UserEntity {

    /** 🔑 Identificador único del usuario (clave primaria). */
    @Id
    private Long id;

    /** 🧍‍♀️ Nombre completo del usuario. Campo obligatorio. */
    @Column(nullable = false)
    private String name;

    /** 📧 Correo electrónico del usuario. Único y obligatorio. */
    @Column(nullable = false, unique = true)
    private String email;

    /** 🏗️ Constructor por defecto requerido por JPA. */
    public UserEntity() {}

    /**
     * 🧱 Constructor completo para crear una entidad manualmente.
     *
     * @param id    identificador único del usuario
     * @param name  nombre del usuario
     * @param email correo electrónico del usuario
     */
    public UserEntity(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    /** @return identificador único del usuario */
    public Long getId() { return id; }

    /** @param id establece el identificador único del usuario */
    public void setId(Long id) { this.id = id; }

    /** @return nombre del usuario */
    public String getName() { return name; }

    /** @param name establece el nombre del usuario */
    public void setName(String name) { this.name = name; }

    /** @return correo electrónico del usuario */
    public String getEmail() { return email; }

    /** @param email establece el correo electrónico del usuario */
    public void setEmail(String email) { this.email = email; }
}
