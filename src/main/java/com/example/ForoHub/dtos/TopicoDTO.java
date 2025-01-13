// Ubicación: com.example.ForoHub.dtos.TopicoDTO.java
package com.example.ForoHub.dtos;

import java.util.List;

/**
 * Representa los datos simplificados de un tópico.
 * Este record se utiliza para enviar y recibir información de tópicos a través de la API.
 *
 * @param id Identificador único del tópico.
 * @param titulo Título del tópico.
 * @param mensaje Contenido del mensaje del tópico.
 * @param autorUsername Nombre de usuario del autor del tópico.
 * @param categoria Categoría del tópico.
 * @param etiquetas Lista de etiquetas asociadas al tópico.
 * @param cursoNombre Nombre del curso al que pertenece el tópico.
 */
public record TopicoDTO(Long id, String titulo, String mensaje, String autorUsername, String categoria, List<String> etiquetas, String cursoNombre) {}
