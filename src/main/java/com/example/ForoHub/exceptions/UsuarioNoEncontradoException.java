package com.example.ForoHub.exceptions;

public class UsuarioNoEncontradoException extends RuntimeException {

    public UsuarioNoEncontradoException() {
        super("Usuario no encontrado");
    }

    public UsuarioNoEncontradoException(String message) {
        super(message);
    }

    public UsuarioNoEncontradoException(String message, Throwable cause) {
        super(message, cause);
    }

    public UsuarioNoEncontradoException(Throwable cause) {
        super(cause);
    }
}
