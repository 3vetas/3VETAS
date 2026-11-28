package com.api.tresvetas.catalogo.dto.patch;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Positive;
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
public class FormatoPatchDTO {

    @Positive
    @Digits(integer = 8, fraction = 4)
    private BigDecimal anchoMm;

    @Positive
    @Digits(integer = 8, fraction = 4)
    private BigDecimal altoMm;
}