package com.api.tresvetas.usuarios.repository;

import com.api.tresvetas.usuarios.entity.Usuario;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 
 *
 * @author Jabier Zurro Aduriz
 */
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByEmail(String email);

}
