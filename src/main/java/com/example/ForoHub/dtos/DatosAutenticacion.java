package com.example.ForoHub.dtos;

import jakarta.validation.constraints.NotBlank;

/**
 * Representa los datos necesarios para autenticar a un usuario.
 *
 * @param username Nombre de usuario o login del usuario.
 * @param password Contraseña del usuario.
 */
public record DatosAutenticacion(
        @NotBlank(message = "El nombre de usuario no puede estar vacío")
        String username,

        @NotBlank(message = "La contraseña no puede estar vacía")
        String password
) {
}
