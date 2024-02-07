package org.puravidatgourmet.security.services;

import java.util.List;
import java.util.Optional;
import org.puravidatgourmet.security.db.repository.MateriaPrimaRepository;
import org.puravidatgourmet.security.domain.entity.MateriaPrima;
import org.puravidatgourmet.security.domain.pojo.MateriaPrimaPojo;
import org.puravidatgourmet.security.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MateriaPrimaService {

  @Autowired private MateriaPrimaRepository materiaPrimaRepository;

  public void deleteById(long id) {
    materiaPrimaRepository.deleteById(id);
  }

  @Transactional
  public MateriaPrima saveMateriaPrima(MateriaPrima materiaPrima) {
    return materiaPrimaRepository.save(materiaPrima);
  }

  public Optional<MateriaPrima> getMateriaPrima(long id) {
    return materiaPrimaRepository.findById(id);
  }

  public List<MateriaPrima> getAllMateriaPrima() {
    return materiaPrimaRepository.findAll();
  }

  public void validateSave(MateriaPrimaPojo materiaPrima) {
    MateriaPrima dbMateriaPrima = materiaPrimaRepository.findByNombre(materiaPrima.getNombre());

    if (dbMateriaPrima != null) {
      throw new BadRequestException("Ya existe una materia prima con ese nombre");
    }
  }

  public void validateUpdate(MateriaPrimaPojo materiaPrima) {
    MateriaPrima dbMateriaPrima = materiaPrimaRepository.findByNombre(materiaPrima.getNombre());

    if (dbMateriaPrima != null && dbMateriaPrima.getId() != materiaPrima.getId()) {
      throw new BadRequestException(
          "Ya existe una materia prima con ese nombre - escoge otro nombre para actualizar");
    }
  }

  public void validateDelete(long materiaPrima) {
    // fixme finish
    // check if ingrediente is been used in any recipe.
  }
}
