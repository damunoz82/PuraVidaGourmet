package org.puravidatgourmet.api.db.repository;

import java.util.Optional;
import org.puravidatgourmet.api.domain.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

  public Optional<Producto> findByNombre(String nombre);
}
