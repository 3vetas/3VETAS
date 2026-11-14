package com.api.tresvetas.catalogo.dto.update;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
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
public class GrosorUpdateDTO {

    @NotNull
    @Positive
    @Digits(integer = 8, fraction = 4)
    private BigDecimal espesorMm;
}