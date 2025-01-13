package com.example.ForoHub.models;

import com.example.ForoHub.enums.EstadoTopico;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private String mensaje;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoTopico status = EstadoTopico.ACTIVO;

    @ManyToOne
    @JoinColumn(name = "autor_id", nullable = false)
    private Usuario autor;

    @ManyToOne
    @JoinColumn(name = "curso_id", nullable = false)
    private Curso curso;

    @ElementCollection
    private List<String> etiquetas;

    @Column(nullable = false)
    private LocalDateTime fechaCreacion = LocalDateTime.now();
}
