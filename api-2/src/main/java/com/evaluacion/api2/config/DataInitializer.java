package com.evaluacion.api2.config;

import com.evaluacion.api2.entity.Usuario;
import com.evaluacion.api2.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initUsers(UsuarioRepository repository, PasswordEncoder encoder) {
        return args -> {
            if (repository.findByUsername("admin").isEmpty()) {
                repository.save(new Usuario("admin", encoder.encode("Admin123")));
            }
        };
    }
}
