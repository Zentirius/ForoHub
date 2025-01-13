package com.example.ForoHub.security;

import com.example.ForoHub.models.Usuario;
import com.example.ForoHub.repositories.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class AdminDataInitializer {

    @Bean
    CommandLineRunner initAdmin(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (usuarioRepository.findByUsername("admin").isEmpty()) {
                Usuario admin = new Usuario();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin123")); // Usa una contraseña segura
                admin.setRoles(Collections.singleton("ROLE_ADMIN"));
                usuarioRepository.save(admin);
            }
        };
    }
}
