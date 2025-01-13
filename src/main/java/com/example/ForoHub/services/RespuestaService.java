package com.example.ForoHub.services;

import com.example.ForoHub.models.Respuesta;
import com.example.ForoHub.repositories.RespuestaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RespuestaService {

    private final RespuestaRepository respuestaRepository;

    public RespuestaService(RespuestaRepository respuestaRepository) {
        this.respuestaRepository = respuestaRepository;
    }

    public Respuesta crearRespuesta(Respuesta respuesta) {
        return respuestaRepository.save(respuesta);
    }

    public List<Respuesta> obtenerRespuestasPorTopico(Long topicoId) {
        return respuestaRepository.findAll()
                .stream()
                .filter(r -> r.getTopico().getId().equals(topicoId))
                .collect(Collectors.toList());
    }
}