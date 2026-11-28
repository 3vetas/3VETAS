package com.api.tresvetas.optimizaciones.mapper;

import com.api.tresvetas.optimizaciones.dto.response.UnidadTableroOptimizacionResponseDTO;
import com.api.tresvetas.optimizaciones.entity.UnidadTableroOptimizacion;
import org.mapstruct.Mapper;

/**
 * @author Jabier Zurro Aduriz
 */
@Mapper(
        componentModel = "spring",
        uses = DistribucionPiezaMapper.class
)
public interface UnidadTableroOptimizacionMapper {

    UnidadTableroOptimizacionResponseDTO toResponseDTO(
            UnidadTableroOptimizacion entity
    );
}
