package com.api.tresvetas.pedidos.dto.patch;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
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
public class PedidoPatchDTO {

    @Positive(message = "El identificador de la dirección de envío debe ser positivo")
    private Integer direccionEnvioId;

    @Positive(message = "El identificador de la dirección de facturación debe ser positivo")
    private Integer direccionFacturacionId;

    @Size(min = 1, message = "El pedido debe contener al menos una optimización")
    private List<@NotNull @Positive Integer> optimizacionesIds;

    @Size(max = 40, message = "El estado no puede superar los 40 caracteres")
    @Pattern(regexp = ".*\\S.*", message = "El estado no puede estar vacío")
    private String estado;

    private String comentario;
}