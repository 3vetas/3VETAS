package com.api.tresvetas.pedidos.mapper;

import com.api.tresvetas.pedidos.dto.response.LineaPedidoResponseDTO;
import com.api.tresvetas.pedidos.entity.LineaPedido;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author Jabier Zurro Aduriz
 */
@Mapper(componentModel = "spring")
public interface LineaPedidoMapper {

    @Mapping(target = "optimizacionId", source = "optimizacion.id")
    LineaPedidoResponseDTO toResponseDTO(LineaPedido entity);
}