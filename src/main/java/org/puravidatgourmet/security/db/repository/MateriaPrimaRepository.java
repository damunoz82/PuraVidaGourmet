package org.puravidatgourmet.security.db.repository;

import org.puravidatgourmet.security.domain.entity.MateriaPrima;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MateriaPrimaRepository  extends JpaRepository<MateriaPrima, Long> {

  public MateriaPrima findByNombre(String nombre);
}
