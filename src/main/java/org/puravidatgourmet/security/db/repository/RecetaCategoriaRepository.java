package org.puravidatgourmet.security.db.repository;

import org.puravidatgourmet.security.domain.entity.RecetaCategoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecetaCategoriaRepository   extends JpaRepository<RecetaCategoria, Long> {

  RecetaCategoria findByNombre(String nombre);
}
