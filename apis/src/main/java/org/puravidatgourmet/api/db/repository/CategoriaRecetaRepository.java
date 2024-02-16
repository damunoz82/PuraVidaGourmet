package org.puravidatgourmet.api.db.repository;

import java.util.Optional;
import org.puravidatgourmet.api.domain.entity.CategoriaReceta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRecetaRepository extends JpaRepository<CategoriaReceta, Long> {

  Optional<CategoriaReceta> findByNombre(String nombre);
}
