package com.psicoclinic.psicoclinic.security; // o el paquete que estés usando

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {

    // Clave secreta para firmar el token. ¡DEBE SER MÁS LARGA EN PRODUCCIÓN!
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    @Value("${jwt.expiration.ms:3600000}") // 1 hora por defecto
    private int jwtExpirationMs;

    public String generateJwtToken(String username) {
        // --- CORRECCIÓN AQUÍ ---
        // Usamos Jwts.builder() y luego signWith(key, algoritmo)
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(key, SignatureAlgorithm.HS512) // Esta es la línea clave corregida
                .compact();
    }

    public boolean validateJwtToken(String authToken) {
        // Aquí ya usabas la sintaxis correcta para parsear (Jwts.parserBuilder().setSigningKey(key).build()...)
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(authToken);
            return true;
        } catch (io.jsonwebtoken.JwtException | IllegalArgumentException e) {
            System.err.println("JWT Error: " + e.getMessage());
        }
        return false;
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getBody().getSubject();
    }
}
