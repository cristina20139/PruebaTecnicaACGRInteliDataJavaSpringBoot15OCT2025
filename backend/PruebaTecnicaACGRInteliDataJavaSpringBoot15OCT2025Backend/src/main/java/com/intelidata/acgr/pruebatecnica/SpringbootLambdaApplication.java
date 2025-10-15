// Indica el paquete raíz del proyecto.
// Todas las demás clases se organizan en subpaquetes bajo este.
package com.intelidata.acgr.pruebatecnica;

// Importa la clase principal de Spring Boot usada para iniciar la aplicación.
import org.springframework.boot.SpringApplication;

// Importa la anotación que configura y arranca automáticamente los componentes de Spring Boot.
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal de la aplicación Spring Boot.
 *
 * Su función es iniciar el contexto de Spring, escanear los componentes,
 * crear los beans, e iniciar el servidor embebido (como Tomcat o Jetty).
 *
 * Esta clase actúa como el punto de entrada del programa, similar a `public static void main()`
 * en cualquier aplicación Java estándar, pero con el poder del framework Spring.
 */
@SpringBootApplication
/*
 * Esta anotación combina tres anotaciones importantes de Spring:
 * 1. @Configuration → Indica que la clase puede contener beans definidos por el usuario.
 * 2. @EnableAutoConfiguration → Activa la configuración automática basada en las dependencias del classpath.
 * 3. @ComponentScan → Permite que Spring busque automáticamente componentes, servicios y controladores
 *    dentro del paquete actual y sus subpaquetes.
 *
 * En resumen: con una sola anotación, se inicializa todo el ecosistema de Spring Boot.
 */
public class SpringbootLambdaApplication {

    /**
     * Método principal de la aplicación (punto de entrada).
     *
     * @param args Argumentos de línea de comandos (opcionales).
     *
     * Dentro de este método, se arranca el contexto de Spring y
     * se lanza el servidor embebido para exponer los endpoints REST definidos.
     */
    public static void main(String[] args) {
        // Llama a SpringApplication.run(), que arranca la aplicación Spring Boot.
        // Esto inicializa todos los beans, configura el contexto de aplicación
        // y ejecuta cualquier CommandLineRunner o ApplicationRunner definido (como DataInitializer).
        SpringApplication.run(SpringbootLambdaApplication.class, args);
    }
}
