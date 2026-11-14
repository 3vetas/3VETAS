
package com.api.tresvetas.proyectos.dto.request;

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
public class TableroProyectoRequestDTO {

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
    private List<PiezaRequestDTO> piezas;
}
