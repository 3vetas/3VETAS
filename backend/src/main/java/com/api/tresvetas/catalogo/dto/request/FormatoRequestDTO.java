package com.api.tresvetas.catalogo.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
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
public class FormatoRequestDTO {

    @NotNull(message = "El ancho es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false,
            message = "El ancho debe ser mayor que cero")
    @Digits(integer = 8, fraction = 4,
            message = "El ancho admite un máximo de 8 dígitos enteros y 4 decimales")
    private BigDecimal anchoMm;

    @NotNull(message = "El alto es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false,
            message = "El alto debe ser mayor que cero")
    @Digits(integer = 8, fraction = 4,
            message = "El alto admite un máximo de 8 dígitos enteros y 4 decimales")
    private BigDecimal altoMm;
}