package com.api.tresvetas.usuarios.dto.response;

import com.api.tresvetas.usuarios.entity.TipoDireccion;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * @author Jabier Zurro Aduriz
 */
@JsonPropertyOrder({
        "id",
        "tipo",
        "direccion",
        "cp",
        "municipio",
        "provincia",
        "pais"
})
public record DireccionResponseDTO(
        Integer id,
        TipoDireccion tipo,
        String direccion,
        String cp,
        String municipio,
        String provincia,
        String pais
) {
}
