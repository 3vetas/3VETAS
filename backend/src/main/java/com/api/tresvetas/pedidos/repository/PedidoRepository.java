package com.api.tresvetas.pedidos.repository;

import com.api.tresvetas.pedidos.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Jabier Zurro Aduriz
 */
@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

}
