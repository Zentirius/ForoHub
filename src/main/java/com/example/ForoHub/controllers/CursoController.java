package com.example.ForoHub.controllers;

import com.example.ForoHub.dtos.CursoDTO;
import com.example.ForoHub.services.CursoService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/cursos")
public class CursoController {

    private final CursoService cursoService;

    public CursoController(CursoService cursoService) {
        this.cursoService = cursoService;
    }

    // Método para listar todos los cursos
    @GetMapping
    public ResponseEntity<List<CursoDTO>> listarCursos() {
        return ResponseEntity.ok(cursoService.listarCursos());
    }

    // Método para obtener un curso por su ID
    @GetMapping("/{id}")
    public ResponseEntity<CursoDTO> obtenerCurso(@PathVariable Long id) {
        return ResponseEntity.ok(cursoService.obtenerCurso(id));
    }

    // Método para crear un curso
    @PostMapping
    public ResponseEntity<CursoDTO> crearCurso(@RequestBody @Valid CursoDTO cursoDTO) {
        return ResponseEntity.ok(cursoService.crearCurso(cursoDTO));
    }

    // Método para eliminar un curso, limitado a usuarios con rol ADMIN
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> eliminarCurso(@PathVariable Long id) {
        cursoService.eliminarCurso(id);
        return ResponseEntity.noContent().build();
    }

    // Método para actualizar un curso
    @PutMapping("/{id}")
    public ResponseEntity<CursoDTO> actualizarCurso(@PathVariable Long id, @RequestBody @Valid CursoDTO cursoDTO) {
        return ResponseEntity.ok(cursoService.actualizarCurso(id, cursoDTO));
    }
}
