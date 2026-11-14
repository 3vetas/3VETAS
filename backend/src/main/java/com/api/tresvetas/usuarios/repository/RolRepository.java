package com.api.tresvetas.usuarios.repository;

import com.api.tresvetas.usuarios.entity.Rol;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 *
 * @author Jabier Zurro Aduriz
 */
public interface RolRepository extends JpaRepository<Rol, Integer> {

    Optional<Rol> findByNombre(String nombre);

    boolean existsByNombre(String nombre);
}
