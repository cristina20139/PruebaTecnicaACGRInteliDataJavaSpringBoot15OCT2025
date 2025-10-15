package com.intelidata.acgr.pruebatecnica.infrastructure.config;

import com.intelidata.acgr.pruebatecnica.domain.model.User;
import com.intelidata.acgr.pruebatecnica.application.port.output.UserWriteRepositoryPort;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Clase de configuración que inicializa datos de ejemplo en la base de datos H2 al arrancar la aplicación.
 * Su propósito es cargar registros de prueba para verificar el correcto funcionamiento de los repositorios.
 */
@Configuration  // Indica que esta clase contiene métodos que definen beans para el contexto de Spring
public class DataInitializer {

    /**
     * Método que define un bean de tipo CommandLineRunner.
     * Este bean se ejecutará automáticamente una vez que la aplicación haya arrancado completamente.
     *
     * @param userWriteRepositoryPort puerto de salida para operaciones de escritura de usuarios.
     * @return una función (lambda) que inserta datos de ejemplo si no existen en la base de datos.
     */
    @Bean  // Registra este método como un bean gestionado por el contenedor de Spring
    CommandLineRunner initDatabase(UserWriteRepositoryPort userWriteRepositoryPort) {
        // Retorna una función lambda que se ejecutará al iniciar la aplicación
        return args -> {
            // Verifica si ya existe un usuario con el correo "ana@example.com" en la base de datos
            if (!userWriteRepositoryPort.existsByEmail("ana@example.com")) {

                // Si no existe, guarda tres usuarios de ejemplo utilizando el puerto de escritura
                userWriteRepositoryPort.save(new User(1L, "Ana Torres", "ana@example.com"));
                userWriteRepositoryPort.save(new User(2L, "Carlos Pérez", "carlos@example.com"));
                userWriteRepositoryPort.save(new User(3L, "Lucía Gómez", "lucia@example.com"));

                // Muestra un mensaje en la consola confirmando que los datos fueron cargados exitosamente
                System.out.println("✅ Datos de prueba cargados en H2");
            }
        };
    }
}
