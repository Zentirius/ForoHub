// Ubicación: com.example.ForoHub.dtos.CursoDTO.java
package com.example.ForoHub.dtos;

import com.example.ForoHub.enums.Categoria;
import java.util.List;


/**
 * Representa los datos simplificados de un curso.
 * Este record se utiliza para enviar y recibir información de cursos a través de la API.
 *
 * @param id Identificador único del curso.
 * @param nombre Nombre del curso.
 * @param categoria Categoría del curso.
 */
public record CursoDTO(Long id, String nombre, String categoria) {} // Ajusta a los valores correctos
