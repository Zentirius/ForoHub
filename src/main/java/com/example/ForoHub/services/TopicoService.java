package com.example.ForoHub.services;

import com.example.ForoHub.dtos.TopicoDTO;
import com.example.ForoHub.models.Topico;
import com.example.ForoHub.models.Usuario;
import com.example.ForoHub.models.Curso;
import com.example.ForoHub.enums.EstadoTopico;
import com.example.ForoHub.repositories.TopicoRepository;
import com.example.ForoHub.repositories.UsuarioRepository;
import com.example.ForoHub.repositories.CursoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class TopicoService {

    private final TopicoRepository topicoRepository;
    private final UsuarioRepository usuarioRepository;
    private final CursoRepository cursoRepository;

    public TopicoService(TopicoRepository topicoRepository, UsuarioRepository usuarioRepository, CursoRepository cursoRepository) {
        this.topicoRepository = topicoRepository;
        this.usuarioRepository = usuarioRepository;
        this.cursoRepository = cursoRepository;
    }

    public Page<TopicoDTO> listarTopicos(Pageable pageable) {
        return topicoRepository.findAll(pageable).map(this::convertirADTO);
    }

    public TopicoDTO obtenerTopico(Long id) {
        return topicoRepository.findById(id)
                .map(this::convertirADTO)
                .orElseThrow(() -> new RuntimeException("Topico no encontrado"));
    }

    @Transactional
    public TopicoDTO crearTopico(TopicoDTO topicoDTO) {
        Usuario autor = usuarioRepository.findByUsername(topicoDTO.autorUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Curso curso = cursoRepository.findByNombre(topicoDTO.cursoNombre())
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));

        Topico topico = new Topico();
        topico.setTitulo(topicoDTO.titulo());
        topico.setMensaje(topicoDTO.mensaje());
        topico.setAutor(autor);
        topico.setCurso(curso);
        topico.setStatus(EstadoTopico.valueOf(topicoDTO.categoria()));
        topico.setEtiquetas(topicoDTO.etiquetas());

        return convertirADTO(topicoRepository.save(topico));
    }

    @Transactional
    public TopicoDTO actualizarTopico(Long id, TopicoDTO topicoDTO) {
        Topico topico = topicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Topico no encontrado"));

        topico.setTitulo(topicoDTO.titulo());
        topico.setMensaje(topicoDTO.mensaje());
        topico.setStatus(EstadoTopico.valueOf(topicoDTO.categoria()));
        topico.setEtiquetas(topicoDTO.etiquetas());

        return convertirADTO(topicoRepository.save(topico));
    }

    @Transactional
    public void eliminarTopico(Long id, Usuario usuario) {
        if (!usuario.getRoles().contains("ROLE_ADMIN")) {
            throw new RuntimeException("Solo un usuario admin puede eliminar tópicos");
        }

        Topico topico = topicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Topico no encontrado"));

        topicoRepository.delete(topico);
    }

    private TopicoDTO convertirADTO(Topico topico) {
        Usuario autor = topico.getAutor();
        Curso curso = topico.getCurso();
        return new TopicoDTO(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                autor != null ? autor.getUsername() : null,
                topico.getStatus() != null ? topico.getStatus().name() : null,
                topico.getEtiquetas(),
                curso != null ? curso.getNombre() : null
        );
    }
}
