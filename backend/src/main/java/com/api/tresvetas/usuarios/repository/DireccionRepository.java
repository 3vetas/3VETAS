package com.api.tresvetas.usuarios.repository;

import com.api.tresvetas.usuarios.entity.Direccion;
import com.api.tresvetas.usuarios.entity.TipoDireccion;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 *
 * @author Jabier Zurro Aduriz
 */
public interface DireccionRepository extends JpaRepository<Direccion, Integer> {

    List<Direccion> findByPersonaId(Integer personaId);

    List<Direccion> findByPersonaIdAndTipo(
            Integer personaId,
            TipoDireccion tipo
    );

    boolean existsByIdAndPersonaId(Integer id, Integer personaId);
}
