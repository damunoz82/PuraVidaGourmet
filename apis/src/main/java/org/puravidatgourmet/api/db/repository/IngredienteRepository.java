package org.puravidatgourmet.api.db.repository;

import org.puravidatgourmet.api.domain.entity.Ingrediente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredienteRepository extends JpaRepository<Ingrediente, Long> {}
