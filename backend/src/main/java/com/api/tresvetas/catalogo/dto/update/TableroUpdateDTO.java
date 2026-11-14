package com.api.tresvetas.catalogo.dto.update;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class TableroUpdateDTO {

    @NotNull
    @Positive
    private Integer tipoTableroId;

    @NotNull
    @Positive
    private Integer acabadoId;

    @NotNull
    @Positive
    private Integer formatoId;

    @NotNull
    @Positive
    private Integer grosorId;

    @NotBlank
    @Size(max = 100)
    private String referencia;

    private String descripcion;

    @NotNull
    @DecimalMin("0.0")
    @Digits(integer = 8, fraction = 2)
    private BigDecimal precioTablero;

    @NotNull
    private Boolean activo;
}