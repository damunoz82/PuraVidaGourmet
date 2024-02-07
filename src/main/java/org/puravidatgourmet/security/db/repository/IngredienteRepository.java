package org.puravidatgourmet.security.db.repository;

import org.puravidatgourmet.security.domain.entity.Ingrediente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredienteRepository   extends JpaRepository<Ingrediente, Long> {}
