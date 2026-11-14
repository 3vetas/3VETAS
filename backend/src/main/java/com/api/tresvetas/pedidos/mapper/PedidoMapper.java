package com.api.tresvetas.pedidos.mapper;

import com.api.tresvetas.pedidos.dto.patch.PedidoPatchDTO;
import com.api.tresvetas.pedidos.dto.request.PedidoRequestDTO;
import com.api.tresvetas.pedidos.dto.update.PedidoUpdateDTO;
import com.api.tresvetas.pedidos.dto.response.PedidoResponseDTO;
import com.api.tresvetas.pedidos.entity.Pedido;
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
        uses = {
            LineaPedidoMapper.class,
            PedidoEstadoMapper.class
        }
)
public interface PedidoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "persona", ignore = true)
    @Mapping(target = "proyecto", ignore = true)
    @Mapping(target = "direccionEnvio", ignore = true)
    @Mapping(target = "direccionFacturacion", ignore = true)
    @Mapping(target = "estado", ignore = true)
    @Mapping(target = "baseImponible", ignore = true)
    @Mapping(target = "descuento", ignore = true)
    @Mapping(target = "importeIva", ignore = true)
    @Mapping(target = "importeFinal", ignore = true)
    @Mapping(target = "fechaPedido", ignore = true)
    @Mapping(target = "fechaActualizacion", ignore = true)
    @Mapping(target = "lineas", ignore = true)
    @Mapping(target = "historialEstados", ignore = true)
    Pedido toEntity(PedidoRequestDTO dto);

    @Mapping(target = "personaId", source = "persona.id")
    @Mapping(target = "proyectoId", source = "proyecto.id")
    @Mapping(target = "direccionEnvioId", source = "direccionEnvio.id")
    @Mapping(target = "direccionFacturacionId", source = "direccionFacturacion.id")
    PedidoResponseDTO toResponseDTO(Pedido entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "persona", ignore = true)
    @Mapping(target = "proyecto", ignore = true)
    @Mapping(target = "direccionEnvio", ignore = true)
    @Mapping(target = "direccionFacturacion", ignore = true)
    @Mapping(target = "estado", ignore = true)
    @Mapping(target = "baseImponible", ignore = true)
    @Mapping(target = "descuento", ignore = true)
    @Mapping(target = "importeIva", ignore = true)
    @Mapping(target = "importeFinal", ignore = true)
    @Mapping(target = "fechaPedido", ignore = true)
    @Mapping(target = "fechaActualizacion", ignore = true)
    @Mapping(target = "lineas", ignore = true)
    @Mapping(target = "historialEstados", ignore = true)
    void updateEntity(PedidoUpdateDTO dto, @MappingTarget Pedido entity);

    @BeanMapping(
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
    )
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "persona", ignore = true)
    @Mapping(target = "proyecto", ignore = true)
    @Mapping(target = "direccionEnvio", ignore = true)
    @Mapping(target = "direccionFacturacion", ignore = true)
    @Mapping(target = "estado", ignore = true)
    @Mapping(target = "baseImponible", ignore = true)
    @Mapping(target = "descuento", ignore = true)
    @Mapping(target = "importeIva", ignore = true)
    @Mapping(target = "importeFinal", ignore = true)
    @Mapping(target = "fechaPedido", ignore = true)
    @Mapping(target = "fechaActualizacion", ignore = true)
    @Mapping(target = "lineas", ignore = true)
    @Mapping(target = "historialEstados", ignore = true)
    void patchEntity(PedidoPatchDTO dto, @MappingTarget Pedido entity);
}
