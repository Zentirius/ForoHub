package com.example.ForoHub.services;

import com.example.ForoHub.dtos.CursoDTO;
import com.example.ForoHub.enums.Categoria;
import com.example.ForoHub.models.Curso;
import com.example.ForoHub.repositories.CursoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CursoService {

    private final CursoRepository cursoRepository;

    public CursoService(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
    }

    public List<CursoDTO> listarCursos() {
        List<Curso> cursos = cursoRepository.findAll();
        return cursos.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public CursoDTO obtenerCurso(Long id) {
        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));
        return convertToDTO(curso);
    }

    public CursoDTO crearCurso(CursoDTO cursoDTO) {
        Curso curso = new Curso();
        curso.setNombre(cursoDTO.nombre());
        curso.setCategoria(Categoria.valueOf(cursoDTO.categoria())); // Ajusta a la categoría
        cursoRepository.save(curso);
        return convertToDTO(curso);
    }

    public void eliminarCurso(Long id) {
        cursoRepository.deleteById(id);
    }

    public CursoDTO actualizarCurso(Long id, CursoDTO cursoDTO) {
        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));

        curso.setNombre(cursoDTO.nombre());
        curso.setCategoria(Categoria.valueOf(cursoDTO.categoria())); // Ajusta a la categoría

        cursoRepository.save(curso);
        return convertToDTO(curso);
    }

    private CursoDTO convertToDTO(Curso curso) {
        return new CursoDTO(
                curso.getId(),
                curso.getNombre(),
                curso.getCategoria().toString() // Adaptar a String
        );
    }
}
