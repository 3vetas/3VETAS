package com.api.tresvetas.pedidos.dto.request;

import jakarta.validation.constraints.NotEmpty;
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
public class PedidoRequestDTO {

    @NotNull(message = "El identificador de la persona es obligatorio")
    @Positive(message = "El identificador de la persona debe ser positivo")
    private Integer personaId;

    @NotNull(message = "El identificador del proyecto es obligatorio")
    @Positive(message = "El identificador del proyecto debe ser positivo")
    private Integer proyectoId;

    @NotNull(message = "La dirección de envío es obligatoria")
    @Positive(message = "El identificador de la dirección de envío debe ser positivo")
    private Integer direccionEnvioId;

    @NotNull(message = "La dirección de facturación es obligatoria")
    @Positive(message = "El identificador de la dirección de facturación debe ser positivo")
    private Integer direccionFacturacionId;

    @NotEmpty(message = "El pedido debe contener al menos una optimización")
    private List<@NotNull @Positive Integer> optimizacionesIds;
}