package org.puravidatgourmet.api.db.repository;

import org.puravidatgourmet.api.domain.entity.RecetaCategoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecetaCategoriaRepository   extends JpaRepository<RecetaCategoria, Long> {

  RecetaCategoria findByNombre(String nombre);
}
