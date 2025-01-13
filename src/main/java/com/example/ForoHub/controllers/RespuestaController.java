package com.example.ForoHub.controllers;

import com.example.ForoHub.dtos.RespuestaDTO;
import com.example.ForoHub.models.Respuesta;
import com.example.ForoHub.services.RespuestaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/respuestas")
public class RespuestaController {

    private final RespuestaService respuestaService;

    public RespuestaController(RespuestaService respuestaService) {
        this.respuestaService = respuestaService;
    }

    @PostMapping
    public ResponseEntity<RespuestaDTO> crearRespuesta(@RequestBody @Valid RespuestaDTO respuestaDTO) {
        Respuesta nuevaRespuesta = new Respuesta();
        nuevaRespuesta.setContenido(respuestaDTO.contenido());
        nuevaRespuesta.setFechaCreacion(LocalDateTime.now());
        nuevaRespuesta.setSolucion(false);

        Respuesta guardada = respuestaService.crearRespuesta(nuevaRespuesta);
        return ResponseEntity.ok(new RespuestaDTO(
                guardada.getId(),
                guardada.getContenido(),
                guardada.getFechaCreacion(),
                guardada.getTopico().getId(),
                guardada.getAutor().getId(),
                guardada.isSolucion()
        ));
    }
}
