package com.api.tresvetas.optimizaciones.mapper;

import com.api.tresvetas.optimizaciones.dto.response.DetalleOptimizacionResponseDTO;
import com.api.tresvetas.optimizaciones.entity.DetalleOptimizacion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author Jabier Zurro Aduriz
 */
@Mapper(
        componentModel = "spring",
        uses = UnidadTableroOptimizacionMapper.class
)
public interface DetalleOptimizacionMapper {

    @Mapping(target = "tableroId", source = "tablero.id")
    DetalleOptimizacionResponseDTO toResponseDTO(
            DetalleOptimizacion entity
    );
}
