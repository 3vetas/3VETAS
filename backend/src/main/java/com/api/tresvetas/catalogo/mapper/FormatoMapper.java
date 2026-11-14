package com.api.tresvetas.catalogo.mapper;

import com.api.tresvetas.catalogo.dto.patch.FormatoPatchDTO;
import com.api.tresvetas.catalogo.dto.request.FormatoRequestDTO;
import com.api.tresvetas.catalogo.dto.response.FormatoResponseDTO;
import com.api.tresvetas.catalogo.dto.update.FormatoUpdateDTO;
import com.api.tresvetas.catalogo.entity.Formato;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * @author Jabier Zurro Aduriz
 */
@Mapper(componentModel = "spring")
public interface FormatoMapper {

    @Mapping(target = "id", ignore = true)
    Formato toEntity(FormatoRequestDTO dto);

    FormatoResponseDTO toResponseDTO(Formato entity);

    @Mapping(target = "id", ignore = true)
    void updateEntity(
            FormatoUpdateDTO dto,
            @MappingTarget Formato entity
    );

    @BeanMapping(
            nullValuePropertyMappingStrategy =
                    NullValuePropertyMappingStrategy.IGNORE
    )
    @Mapping(target = "id", ignore = true)
    void patchEntity(
            FormatoPatchDTO dto,
            @MappingTarget Formato entity
    );
}
