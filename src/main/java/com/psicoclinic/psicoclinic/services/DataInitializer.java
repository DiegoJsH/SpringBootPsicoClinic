package com.psicoclinic.psicoclinic.services;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.psicoclinic.psicoclinic.models.UserModel;
import com.psicoclinic.psicoclinic.repositories.UserRepository;

@Configuration
public class DataInitializer {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Usuarios iniciales + roles
    private static final List<String> INITIAL_USERS = Arrays.asList(
        "PSICOLOGO",
        "ADMI",
        "SOPORTE"
    );

    private static final List<String> ROLES = Arrays.asList(
        "PSICOLOGO",
        "ADMIN",
        "SOPORTE"
    );

    private static final String DEFAULT_PASSWORD = "123";

    @Bean
    public CommandLineRunner initializeUsers() {
        return args -> {
            String hashedPassword = passwordEncoder.encode(DEFAULT_PASSWORD);
            int createdCount = 0;

            System.out.println("--- Inicializando usuarios de seguridad con roles ---");

            for (int i = 0; i < INITIAL_USERS.size(); i++) {
                String username = INITIAL_USERS.get(i);
                String role = ROLES.get(i);

                // 1. Revisar si el usuario ya existe
                if (userRepository.findByUsername(username).isEmpty()) {

                    // 2. Crear y guardar el nuevo usuario
                    UserModel u = new UserModel();
                    u.setUsername(username);
                    u.setPassword(hashedPassword);
                    u.setRole(role);

                    userRepository.save(u);

                    System.out.println("✅ Creado usuario: " + username + " | Rol: " + role);
                    createdCount++;
                }
            }

            if (createdCount > 0) {
                System.out.println("✅ Total creados: " + createdCount + " usuarios. Contraseña = " + DEFAULT_PASSWORD);
            } else {
                System.out.println("ℹ️Todos los usuarios ya existían.");
            }
        };
    }
}
