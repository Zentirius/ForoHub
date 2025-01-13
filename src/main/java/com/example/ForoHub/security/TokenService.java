package com.example.ForoHub.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {

    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.secret}")
    private String secret;

    public String generateToken(String username) {
        if (secret == null || secret.isBlank()) { // Corregido aquí
            throw new IllegalStateException("La clave secreta para JWT no está configurada.");
        }

        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer(issuer)
                    .withSubject(username)
                    .withIssuedAt(new Date())
                    .withExpiresAt(new Date(System.currentTimeMillis() + 86400000)) // 1 día de expiración
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error al generar el token JWT", exception);
        }
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm).withIssuer(issuer).build();
            return verifier.verify(token).getSubject();
        } catch (JWTVerificationException exception) {
            return null;
        }
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        String username = validateToken(token);
        return username != null && username.equals(userDetails.getUsername());
    }
}
