package com.example.ForoHub.exceptions;

/**
 * Excepción personalizada para manejar errores de validación.
 */
public class ValidacionExcepcion extends RuntimeException {

    /**
     * Constructor de la excepción con un mensaje personalizado.
     *
     * @param mensaje Mensaje que describe el error de validación.
     */
    public ValidacionExcepcion(String mensaje) {
        super(mensaje);
    }
}
