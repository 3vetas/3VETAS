package com.api.tresvetas.pedidos.mapper;

import com.api.tresvetas.pedidos.dto.response.PedidoEstadoResponseDTO;
import com.api.tresvetas.pedidos.entity.PedidoEstado;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author Jabier Zurro Aduriz
 */
@Mapper(componentModel = "spring")
public interface PedidoEstadoMapper {

    @Mapping(target = "usuarioId", source = "usuario.id")
    PedidoEstadoResponseDTO toResponseDTO(PedidoEstado entity);
}
