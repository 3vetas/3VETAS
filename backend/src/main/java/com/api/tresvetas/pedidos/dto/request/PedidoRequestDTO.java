package com.api.tresvetas.pedidos.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
    private Integer personaId;

    @NotNull(message = "El identificador del proyecto es obligatorio")
    private Integer proyectoId;

    @NotNull(message = "La dirección de envío es obligatoria")
    private Integer direccionEnvioId;

    @NotNull(message = "La dirección de facturación es obligatoria")
    private Integer direccionFacturacionId;

    @NotEmpty(message = "El pedido debe contener al menos una optimización")
    private List<@NotNull Integer> optimizacionesIds;
}