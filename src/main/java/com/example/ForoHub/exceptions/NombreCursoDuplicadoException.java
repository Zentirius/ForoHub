package com.example.ForoHub.exceptions;

/**
 * Excepción personalizada para manejar casos en los que el nombre del curso ya existe.
 */
public class NombreCursoDuplicadoException extends RuntimeException {

    /**
     * Constructor de la excepción con un mensaje personalizado.
     *
     * @param mensaje Mensaje que describe el error.
     */
    public NombreCursoDuplicadoException(String mensaje) {
        super(mensaje);
    }
}
