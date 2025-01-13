package com.example.ForoHub.exceptions;


/**
 * Excepción personalizada para manejar casos en los que un recurso no se encuentra.
 */
public class RecursoNoEncontradoException extends RuntimeException {

    /**
     * Constructor de la excepción con un mensaje personalizado.
     *
     * @param mensaje Mensaje que describe el error.
     */
    public RecursoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
