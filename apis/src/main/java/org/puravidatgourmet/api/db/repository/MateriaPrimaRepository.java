package org.puravidatgourmet.api.db.repository;

import java.util.Optional;
import org.puravidatgourmet.api.domain.entity.MateriaPrima;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MateriaPrimaRepository extends JpaRepository<MateriaPrima, Long> {

  public Optional<MateriaPrima> findByNombre(String nombre);
}
