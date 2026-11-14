package com.api.tresvetas.proyectos.dto.update;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 *
 * @author Jabier Zurro Aduriz
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TableroProyectoUpdateDTO {

    @NotNull
    @Positive
    private Integer id;

    @NotNull
    @Positive
    private Integer tipoTableroId;

    @NotNull
    @Positive
    private Integer acabadoId;

    @NotNull
    @Positive
    private Integer grosorId;

    @Valid
    private List<PiezaUpdateDTO> piezas;
}
