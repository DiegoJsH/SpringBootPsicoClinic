package com.psicoclinic.psicoclinic.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.psicoclinic.psicoclinic.models.UserModel;
import com.psicoclinic.psicoclinic.repositories.UserRepository;
import com.psicoclinic.psicoclinic.security.JwtUtils;
import com.psicoclinic.psicoclinic.security.payloads.AuthRequest;
import com.psicoclinic.psicoclinic.security.payloads.JwtResponse;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;

    public AuthController(AuthenticationManager authenticationManager, JwtUtils jwtUtils, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody AuthRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 🔥 Generar token JWT
        String jwt = jwtUtils.generateJwtToken(loginRequest.getUsername());

        // 🔥 Obtener datos del usuario autenticado
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        UserModel user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // 🔥 DEVOLVER username + token + ROLE (MUY IMPORTANTE)
        return ResponseEntity.ok(
                new JwtResponse(
                        jwt,
                        user.getUsername(),
                        user.getRole() // <--- AHORA SÍ ENVÍA EL ROL
                )
        );
    }
}
