package com.api.tresvetas.pedidos.repository;

import com.api.tresvetas.pedidos.entity.PedidoEstado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Jabier Zurro Aduriz
 */
@Repository
public interface PedidoEstadoRepository extends JpaRepository<PedidoEstado, Integer> {

}