package org.puravidatgourmet.api.db.repository;

import org.puravidatgourmet.api.domain.entity.Receta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecetaRepository extends JpaRepository<Receta, Long> {

  public Receta findByNombre(String nombre);

//  public List<Receta> findByCategoriaNombre(String categoria);
}
