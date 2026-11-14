package com.api.tresvetas.proyectos.dto.patch;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Positive;
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
public class PiezaPatchDTO {

    @Size(max = 255)
    private String descripcion;

    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer = 8, fraction = 4)
    private BigDecimal anchoMm;

    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer = 8, fraction = 4)
    private BigDecimal altoMm;

    @Positive
    private Integer cantidad;

    private Boolean rotacionPermitida;
}
