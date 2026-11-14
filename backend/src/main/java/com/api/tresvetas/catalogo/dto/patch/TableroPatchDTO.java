package com.api.tresvetas.catalogo.dto.patch;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
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
public class TableroPatchDTO {

    @Positive
    private Integer tipoTableroId;

    @Positive
    private Integer acabadoId;

    @Positive
    private Integer formatoId;

    @Positive
    private Integer grosorId;

    @Pattern(
            regexp = ".*\\S.*",
            message = "La referencia no puede estar vacía"
    )
    @Size(max = 100)
    private String referencia;

    private String descripcion;

    @DecimalMin("0.0")
    @Digits(integer = 8, fraction = 2)
    private BigDecimal precioTablero;

    private Boolean activo;
}