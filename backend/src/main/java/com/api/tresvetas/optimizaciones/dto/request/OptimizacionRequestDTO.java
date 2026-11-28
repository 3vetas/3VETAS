package com.api.tresvetas.optimizaciones.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
public class OptimizacionRequestDTO {

    @NotNull(message = "La agrupación de tableros es obligatoria")
    @Positive(message = "El identificador de la agrupación debe ser positivo")
    private Integer tableroProyectoId;
}
