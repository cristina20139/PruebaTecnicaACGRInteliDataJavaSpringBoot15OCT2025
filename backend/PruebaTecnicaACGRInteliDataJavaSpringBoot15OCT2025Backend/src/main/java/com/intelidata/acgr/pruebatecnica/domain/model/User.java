// com/intelidata/acgr/pruebatecnica/domain/model/User.java
package com.intelidata.acgr.pruebatecnica.domain.model;

import java.util.Objects;

import io.swagger.v3.oas.annotations.media.Schema;


/**
 * 🧩 <b>Entidad de Dominio — User</b><br>
 *
 * Esta clase representa la <b>entidad de dominio</b> que modela a un usuario dentro del
 * contexto del sistema.
 * En la arquitectura hexagonal (Ports and Adapters), las entidades del dominio son el núcleo
 * de la aplicación y contienen los datos fundamentales junto con las reglas básicas de consistencia. ⚙️
 *
 * <p><b>📍 Rol en la arquitectura:</b></p>
 * <ul>
 *   <li>Forma parte del <b>Dominio</b> (Domain Layer), el corazón de la aplicación.</li>
 *   <li>Define el modelo de negocio “Usuario” de forma pura, sin depender de frameworks
 *       ni de detalles técnicos como JPA o JSON. 🚫</li>
 *   <li>Es utilizada por los casos de uso (servicios de aplicación) y por los adaptadores de persistencia.</li>
 * </ul>
 *
 * <p><b>🧠 Principios SOLID aplicados:</b></p>
 * <ul>
 *   <li><b>SRP (Single Responsibility Principle):</b> esta clase tiene una única responsabilidad:
 *       representar los datos y la identidad de un usuario en el dominio.</li>
 *   <li><b>DIP (Dependency Inversion Principle):</b> no depende de clases concretas externas;
 *       se mantiene totalmente independiente de infraestructura o frameworks.</li>
 * </ul>
 *
 * <p><b>🔹 Atributos principales:</b></p>
 * <ul>
 *   <li><b>id:</b> identificador único del usuario dentro del sistema.</li>
 *   <li><b>name:</b> nombre del usuario, utilizado para identificación humana.</li>
 *   <li><b>email:</b> dirección de correo electrónico que actúa también como identificador natural.</li>
 * </ul>
 *
 * <p><b>📦 Uso típico en la arquitectura:</b></p>
 * <ul>
 *   <li>📥 Se recibe en los controladores REST como parte del cuerpo de una solicitud (por ejemplo, al registrar un nuevo usuario).</li>
 *   <li>🔁 Se transforma en un objeto de persistencia ({@code UserEntity}) en el adaptador JPA antes de guardarse en la base de datos.</li>
 *   <li>📤 Se devuelve a la capa REST cuando se consultan los usuarios desde la base de datos.</li>
 * </ul>
 *
 * <p><b>⚙️ Métodos clave:</b></p>
 * <ul>
 *   <li>{@link #equals(Object)} y {@link #hashCode()} — redefinidos para garantizar
 *       la igualdad basada en el identificador y el email (identidad lógica del usuario).</li>
 *   <li>Constructores y getters/setters simples — mantienen la clase como un POJO limpio
 *       y compatible con frameworks de serialización o mapeo.</li>
 * </ul>
 *
 * <p><b>🧱 Buenas prácticas aplicadas:</b></p>
 * <ul>
 *   <li>El dominio está completamente desacoplado de la base de datos (no hay anotaciones JPA aquí).</li>
 *   <li>Es fácilmente testeable y serializable.</li>
 *   <li>Puede evolucionar sin afectar la infraestructura (por ejemplo, agregar validaciones, reglas de negocio, etc.).</li>
 * </ul>
 *
 * <p><b>💡 Ejemplo de uso:</b></p>
 * <pre>{@code
 * User user = new User(1L, "Aura Garzón", "aura.garzon@intelidata.com");
 * System.out.println("Usuario: " + user.getName());
 * }</pre>
 *
 * <p><b>📘 Nota:</b> En versiones más avanzadas, podrían agregarse validaciones dentro del constructor
 * (por ejemplo, verificar formato del correo o longitud del nombre) para fortalecer la lógica de dominio. ✅</p>
 *
 * @author
 * 👩‍💻 <b>Aura Cristina Garzón Rodríguez</b>
 * @since Octubre 15, 2025
 */
@Schema(description = "Entidad que representa un usuario del sistema")
public class User {

    /** Identificador único del usuario dentro del sistema. */
    @Schema(description = "Identificador único del usuario", example = "1")
    private Long id;

    /** Nombre completo del usuario. */
    @Schema(description = "Nombre completo del usuario", example = "Ana Torres")
    private String name;

    /** Correo electrónico único del usuario (clave natural del dominio). */
    @Schema(description = "Correo electrónico del usuario", example = "ana@example.com")
    private String email;

    /** Constructor vacío necesario para serialización y frameworks. */
    public User() {}

    /**
     * Constructor principal que inicializa los campos del usuario.
     *
     * @param id identificador único.
     * @param name nombre del usuario.
     * @param email correo electrónico del usuario.
     */
    public User(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    /** @return identificador del usuario. */
    public Long getId() { return id; }

    /** @param id identificador del usuario. */
    public void setId(Long id) { this.id = id; }

    /** @return nombre del usuario. */
    public String getName() { return name; }

    /** @param name nombre del usuario. */
    public void setName(String name) { this.name = name; }

    /** @return correo electrónico del usuario. */
    public String getEmail() { return email; }

    /** @param email correo electrónico del usuario. */
    public void setEmail(String email) { this.email = email; }

    /**
     * Determina si dos usuarios son iguales basándose en su identificador y correo electrónico.
     *
     * @param o objeto a comparar.
     * @return {@code true} si ambos representan el mismo usuario lógico.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(email, user.email);
    }

    /**
     * Calcula el código hash del usuario basado en su identidad lógica.
     *
     * @return valor hash consistente con {@link #equals(Object)}.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, email);
    }
}
