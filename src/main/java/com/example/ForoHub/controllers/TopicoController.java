package com.example.ForoHub.controllers;

import com.example.ForoHub.dtos.TopicoDTO;
import com.example.ForoHub.models.Usuario;
import com.example.ForoHub.services.TopicoService;
import com.example.ForoHub.repositories.UsuarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    private final TopicoService topicoService;
    private final UsuarioRepository usuarioRepository;

    public TopicoController(TopicoService topicoService, UsuarioRepository usuarioRepository) {
        this.topicoService = topicoService;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping
    public ResponseEntity<Page<TopicoDTO>> listarTopicos(Pageable pageable) {
        return ResponseEntity.ok(topicoService.listarTopicos(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicoDTO> obtenerTopico(@PathVariable Long id) {
        return ResponseEntity.ok(topicoService.obtenerTopico(id));
    }

    @PostMapping
    public ResponseEntity<TopicoDTO> crearTopico(@RequestBody TopicoDTO topicoDTO) {
        return ResponseEntity.ok(topicoService.crearTopico(topicoDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TopicoDTO> actualizarTopico(@PathVariable Long id, @RequestBody TopicoDTO topicoDTO) {
        return ResponseEntity.ok(topicoService.actualizarTopico(id, topicoDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> eliminarTopico(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        Usuario usuario = usuarioRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        topicoService.eliminarTopico(id, usuario);
        return ResponseEntity.noContent().build();
    }
}
