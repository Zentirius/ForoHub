package com.example.ForoHub.dtos;

import java.time.LocalDateTime;

public record RespuestaDTO(
        Long id,
        String contenido,
        LocalDateTime fechaCreacion,
        Long topicoId,
        Long autorId,
        boolean solucion
) {}
