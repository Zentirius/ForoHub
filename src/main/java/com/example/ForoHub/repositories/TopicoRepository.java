package com.example.ForoHub.repositories;


import com.example.ForoHub.models.Topico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Repositorio JPA para la gestión de tópicos.
 */
public interface TopicoRepository extends JpaRepository<Topico, Long> {
    @Query("SELECT t FROM Topico t WHERE t.curso.nombre = :cursoNombre AND EXTRACT(YEAR FROM t.fechaCreacion) = :year")
    Page<Topico> findByCursoAndYear(@Param("cursoNombre") String cursoNombre, @Param("year") int year, Pageable pageable);
}
