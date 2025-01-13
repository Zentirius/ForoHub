package com.example.ForoHub.repositories;

import com.example.ForoHub.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio para realizar operaciones CRUD en la entidad Usuario.
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    /**
     * Buscar un usuario por su nombre de usuario.
     *
     * @param username El nombre de usuario a buscar.
     * @return Usuario encontrado, envuelto en un Optional.
     */
    Optional<Usuario> findByUsername(String username);
}
