package com.api.tresvetas.documentos.repository;

import com.api.tresvetas.documentos.entity.Documento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Jabier Zurro Aduriz
 */
@Repository
public interface DocumentoRepository extends JpaRepository<Documento, Integer> {

}