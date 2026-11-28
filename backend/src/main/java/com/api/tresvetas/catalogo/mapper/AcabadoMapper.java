package com.api.tresvetas.catalogo.mapper;

import com.api.tresvetas.catalogo.dto.patch.AcabadoPatchDTO;
import com.api.tresvetas.catalogo.dto.request.AcabadoRequestDTO;
import com.api.tresvetas.catalogo.dto.response.AcabadoResponseDTO;
import com.api.tresvetas.catalogo.dto.update.AcabadoUpdateDTO;
import com.api.tresvetas.catalogo.entity.Acabado;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * @author Jabier Zurro Aduriz
 */
@Mapper(componentModel = "spring")
public interface AcabadoMapper {

    @Mapping(target = "id", ignore = true)
    Acabado toEntity(AcabadoRequestDTO dto);

    AcabadoResponseDTO toResponseDTO(Acabado entity);

    @Mapping(target = "id", ignore = true)
    void updateEntity(
            AcabadoUpdateDTO dto,
            @MappingTarget Acabado entity
    );

    @BeanMapping(
            nullValuePropertyMappingStrategy =
                    NullValuePropertyMappingStrategy.IGNORE
    )
    @Mapping(target = "id", ignore = true)
    void patchEntity(
            AcabadoPatchDTO dto,
            @MappingTarget Acabado entity
    );
}
