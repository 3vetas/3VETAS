package com.api.tresvetas.pedidos.dto.update;

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
public class PedidoUpdateDTO {

    @NotNull(message = "La dirección de envío es obligatoria")
    @Positive(message = "El identificador de la dirección de envío debe ser positivo")
    private Integer direccionEnvioId;

    @NotNull(message = "La dirección de facturación es obligatoria")
    @Positive(message = "El identificador de la dirección de facturación debe ser positivo")
    private Integer direccionFacturacionId;

    @NotEmpty(message = "El pedido debe contener al menos una optimización")
    private List<@NotNull @Positive Integer> optimizacionesIds;
}
