package com.api.tresvetas.usuarios.repository;

import com.api.tresvetas.usuarios.entity.Persona;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 *
 * @author Jabier Zurro Aduriz
 */
public interface PersonaRepository extends JpaRepository<Persona, Integer> {

    Optional<Persona> findByUsuarioId(Integer usuarioId);

    boolean existsByUsuarioId(Integer usuarioId);
}
