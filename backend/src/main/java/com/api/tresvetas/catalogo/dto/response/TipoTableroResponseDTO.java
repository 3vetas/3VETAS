package com.api.tresvetas.catalogo.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 *
 * @author Jabier Zurro Aduriz
 */
@JsonPropertyOrder({
    "id",
    "nombre",
    "descripcion",
    "imagen"
})
public record TipoTableroResponseDTO(
        Integer id,
        String nombre,
        String descripcion,
        String imagen
) {
}