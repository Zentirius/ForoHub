package com.example.ForoHub.services;

import com.example.ForoHub.models.Usuario;
import com.example.ForoHub.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Cargar un usuario por su nombre de usuario.
     *
     * @param username Nombre de usuario.
     * @return Detalles del usuario autenticado.
     * @throws UsernameNotFoundException Si el usuario no existe.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));
    }

    /**
     * Crear un nuevo usuario en la base de datos.
     *
     * @param usuario Datos del usuario a guardar.
     * @return Usuario creado.
     */
    public Usuario crearUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    /**
     * Obtener un usuario por su ID.
     *
     * @param id ID del usuario.
     * @return Usuario encontrado.
     * @throws UsernameNotFoundException Si no se encuentra el usuario.
     */
    public Usuario obtenerUsuarioPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con ID: " + id));
    }
}
