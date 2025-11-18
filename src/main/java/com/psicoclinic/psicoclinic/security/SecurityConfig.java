package com.psicoclinic.psicoclinic.security;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtRequestFilter jwtRequestFilter;

    public SecurityConfig(JwtRequestFilter jwtRequestFilter) {
        this.jwtRequestFilter = jwtRequestFilter;
    }

    // Bean para el encriptado de contraseñas
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Bean para el gestor de autenticación
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // =======================================================
    // 🚩 CONFIGURACIÓN DE CORS
    // NOTA: Esta es la ÚNICA configuración de CORS necesaria.
    // No se debe duplicar en WebConfig porque Spring Security procesa primero.
    // =======================================================
    @Bean
    @SuppressWarnings("unused") // Spring usa este bean automáticamente
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Orígenes permitidos (frontend)
        configuration.setAllowedOrigins(Arrays.asList(
                "http://127.0.0.1:3000", // Desarrollo local
                "http://localhost:3000", // Desarrollo local alternativo
                "https://diegojsh.github.io" // Producción (GitHub Pages)
        ));

        // Métodos HTTP permitidos
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // Headers permitidos (incluye Authorization para JWT)
        configuration.setAllowedHeaders(Arrays.asList("*"));

        // Headers expuestos al cliente
        configuration.setExposedHeaders(Arrays.asList("Authorization"));

        // Permite credenciales (cookies, authorization headers)
        configuration.setAllowCredentials(true);

        // Registrar configuración para todos los endpoints
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
    // =======================================================

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .cors(Customizer.withDefaults()) // Usa la configuración CORS del bean
                .csrf(csrf -> csrf.disable()) // Deshabilitado porque usamos JWT (stateless)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Sin sesiones
                .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**", "/auth/**", "/public/**").permitAll() // Login público
                .requestMatchers("/staff/**").hasRole("ADMIN") // Solo ADMIN puede acceder a /staff
                .anyRequest().authenticated() // Todo lo demás requiere autenticación
                )
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class); // Filtro JWT

        return http.build();
    }
}
