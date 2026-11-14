package com.api.tresvetas.optimizaciones.mapper;

import com.api.tresvetas.optimizaciones.dto.patch.OptimizacionPatchDTO;
import com.api.tresvetas.optimizaciones.dto.request.OptimizacionRequestDTO;
import com.api.tresvetas.optimizaciones.dto.response.OptimizacionResponseDTO;
import com.api.tresvetas.optimizaciones.entity.Optimizacion;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * @author Jabier Zurro Aduriz
 */
@Mapper(
        componentModel = "spring",
        uses = DetalleOptimizacionMapper.class
)
public interface OptimizacionMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tableroProyecto", ignore = true)
    @Mapping(target = "nombreOpcion", ignore = true)
    @Mapping(target = "superficieUtilizada", ignore = true)
    @Mapping(target = "superficieDesperdiciada", ignore = true)
    @Mapping(target = "porcentajeAprovechamiento", ignore = true)
    @Mapping(target = "costeEstimado", ignore = true)
    @Mapping(target = "seleccionada", ignore = true)
    @Mapping(target = "fechaCalculo", ignore = true)
    @Mapping(target = "detalles", ignore = true)
    Optimizacion toEntity(OptimizacionRequestDTO dto);

    @Mapping(target = "tableroProyectoId", source = "tableroProyecto.id")
    OptimizacionResponseDTO toResponseDTO(Optimizacion entity);

    @BeanMapping(
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
    )
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tableroProyecto", ignore = true)
    @Mapping(target = "nombreOpcion", ignore = true)
    @Mapping(target = "superficieUtilizada", ignore = true)
    @Mapping(target = "superficieDesperdiciada", ignore = true)
    @Mapping(target = "porcentajeAprovechamiento", ignore = true)
    @Mapping(target = "costeEstimado", ignore = true)
    @Mapping(target = "fechaCalculo", ignore = true)
    @Mapping(target = "detalles", ignore = true)
    void patchEntity(
            OptimizacionPatchDTO dto,
            @MappingTarget Optimizacion entity
    );
}
