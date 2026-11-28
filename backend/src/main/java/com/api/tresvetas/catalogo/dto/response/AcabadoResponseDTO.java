package com.api.tresvetas.catalogo.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 *
 * @author Jabier Zurro Aduriz
 */
@JsonPropertyOrder({
    "id",
    "familia",
    "nombre",
    "colorBase",
    "textura",
    "fabricante",
    "codFabricante"
})
public record AcabadoResponseDTO(
        Integer id,
        String familia,
        String nombre,
        String colorBase,
        String textura,
        String fabricante,
        String codFabricante
) {
}