package com.api.tresvetas.optimizaciones.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author Jabier Zurro Aduriz
 */
@JsonPropertyOrder({
    "id",
    "tableroProyectoId",
    "nombreOpcion",
    "superficieUtilizada",
    "superficieDesperdiciada",
    "porcentajeAprovechamiento",
    "costeEstimado",
    "seleccionada",
    "fechaCalculo",
    "detalles"
})
public record OptimizacionResponseDTO(
        Integer id,
        Integer tableroProyectoId,
        String nombreOpcion,
        BigDecimal superficieUtilizada,
        BigDecimal superficieDesperdiciada,
        BigDecimal porcentajeAprovechamiento,
        BigDecimal costeEstimado,
        Boolean seleccionada,
        LocalDateTime fechaCalculo,
        List<DetalleOptimizacionResponseDTO> detalles
) {

}
