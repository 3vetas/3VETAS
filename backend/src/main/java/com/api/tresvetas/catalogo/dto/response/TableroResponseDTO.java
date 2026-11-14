package com.api.tresvetas.catalogo.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.math.BigDecimal;

/**
 *
 * @author Jabier Zurro Aduriz
 */
@JsonPropertyOrder({
    "id",
    "tipo_tablero_id",
    "acabado_id",
    "formato_id",
    "grosor_id",
    "referencia",
    "descripcion",
    "precio_tablero",
    "stock_actual",
    "activo"
})
public record TableroResponseDTO(
        Integer id,
        Integer tipoTableroId,
        Integer acabadoId,
        Integer formatoId,
        Integer grosorId,
        String referencia,
        String descripcion,
        BigDecimal precioTablero,
        Integer stockActual,
        Boolean activo
) {
}