package com.api.tresvetas.optimizaciones.mapper;

import com.api.tresvetas.optimizaciones.dto.response.DistribucionPiezaResponseDTO;
import com.api.tresvetas.optimizaciones.entity.DistribucionPieza;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author Jabier Zurro Aduriz
 */
@Mapper(componentModel = "spring")
public interface DistribucionPiezaMapper {

    @Mapping(target = "piezaId", source = "pieza.id")
    DistribucionPiezaResponseDTO toResponseDTO(DistribucionPieza entity);
}
