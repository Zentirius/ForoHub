package com.example.ForoHub.repositories;

import com.example.ForoHub.models.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {

    /**
     * Buscar un curso por su nombre.
     *
     * @param nombre Nombre del curso.
     * @return Curso encontrado, envuelto en un Optional.
     */
    Optional<Curso> findByNombre(String nombre);

    /**
     * Buscar un curso con sus tópicos.
     *
     * @param id ID del curso.
     * @return Curso encontrado con tópicos, envuelto en un Optional.
     */
    @Query("SELECT c FROM Curso c LEFT JOIN FETCH c.topicos WHERE c.id = :id")
    Optional<Curso> findCursoConTopicos(@Param("id") Long id);
}
