package com.api.tresvetas.proyectos.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.time.LocalDateTime;
import java.util.List;


/**
 *
 * @author Jabier Zurro Aduriz
 */
@JsonPropertyOrder({
    "id",
    "usuarioId",
    "nombre",
    "descripcion",
    "estado",
    "fechaCreacion",
    "fechaModificacion",
    "tableros"
})
public record ProyectoResponseDTO(
        Integer id,
        Integer usuarioId,
        String nombre,
        String descripcion,
        String estado,
        LocalDateTime fechaCreacion,
        LocalDateTime fechaModificacion,
        List<TableroProyectoResponseDTO> tableros
        ) {
}
