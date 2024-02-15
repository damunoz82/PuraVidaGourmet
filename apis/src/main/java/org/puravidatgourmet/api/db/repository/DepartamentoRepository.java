package org.puravidatgourmet.api.db.repository;

import java.util.Optional;
import org.puravidatgourmet.api.domain.entity.Departamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartamentoRepository extends JpaRepository<Departamento, Long> {

  Optional<Departamento> findByNombre(String nombre);
}
