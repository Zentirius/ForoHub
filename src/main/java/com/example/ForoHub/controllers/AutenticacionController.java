package com.example.ForoHub.controllers;

import com.example.ForoHub.dtos.DatosAutenticacion;
import com.example.ForoHub.dtos.DatosTokenJWT;
import com.example.ForoHub.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AutenticacionController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    /**
     * Endpoint para autenticar a un usuario y devolver un token JWT.
     *
     * @param datosAutenticacion Datos necesarios para autenticar al usuario.
     * @return Token JWT generado tras la autenticación exitosa.
     */
    @PostMapping("/login")
    public ResponseEntity<DatosTokenJWT> autenticarUsuario(@RequestBody @Validated DatosAutenticacion datosAutenticacion) {
        try {
            // Crear el token de autenticación
            Authentication authToken = new UsernamePasswordAuthenticationToken(
                    datosAutenticacion.username(),
                    datosAutenticacion.password()
            );

            // Autenticar al usuario
            Authentication usuarioAutenticado = authenticationManager.authenticate(authToken);

            // Obtener el usuario autenticado como UserDetails
            UserDetails usuario = (UserDetails) usuarioAutenticado.getPrincipal();

            // Generar el token JWT
            String tokenJWT = tokenService.generateToken(usuario.getUsername());

            // Retornar el token en la respuesta
            return ResponseEntity.ok(new DatosTokenJWT(tokenJWT));
        } catch (AuthenticationException e) {
            // Manejar la autenticación fallida
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new DatosTokenJWT("Autenticación fallida"));
        }
    }
}