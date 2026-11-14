package com.api.tresvetas.documentos.mapper;

import com.api.tresvetas.documentos.dto.patch.DocumentoPatchDTO;
import com.api.tresvetas.documentos.dto.request.DocumentoRequestDTO;
import com.api.tresvetas.documentos.dto.response.DocumentoResponseDTO;
import com.api.tresvetas.documentos.dto.update.DocumentoUpdateDTO;
import com.api.tresvetas.documentos.entity.Documento;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * @author Jabier Zurro Aduriz
 */
@Mapper(componentModel = "spring")
public interface DocumentoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "optimizacion", ignore = true)
    @Mapping(target = "pedido", ignore = true)
    @Mapping(target = "fechaGeneracion", ignore = true)
    @Mapping(target = "activo", ignore = true)
    Documento toEntity(DocumentoRequestDTO dto);

    @Mapping(target = "optimizacionId", source = "optimizacion.id")
    @Mapping(target = "pedidoId", source = "pedido.id")
    DocumentoResponseDTO toResponseDTO(Documento entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "optimizacion", ignore = true)
    @Mapping(target = "pedido", ignore = true)
    @Mapping(target = "fechaGeneracion", ignore = true)
    @Mapping(target = "activo", ignore = true)
    void updateEntity(
            DocumentoUpdateDTO dto,
            @MappingTarget Documento entity
    );

    @BeanMapping(
            nullValuePropertyMappingStrategy =
                    NullValuePropertyMappingStrategy.IGNORE
    )
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "optimizacion", ignore = true)
    @Mapping(target = "pedido", ignore = true)
    @Mapping(target = "fechaGeneracion", ignore = true)
    void patchEntity(
            DocumentoPatchDTO dto,
            @MappingTarget Documento entity
    );
}
