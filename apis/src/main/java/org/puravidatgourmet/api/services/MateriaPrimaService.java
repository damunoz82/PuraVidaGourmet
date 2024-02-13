package org.puravidatgourmet.api.services;

import java.util.List;
import java.util.Optional;
import org.puravidatgourmet.api.db.repository.MateriaPrimaRepository;
import org.puravidatgourmet.api.domain.entity.MateriaPrima;
import org.puravidatgourmet.api.domain.pojo.MateriaPrimaPojo;
import org.puravidatgourmet.api.exceptions.BadRequestException;
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
    Optional<MateriaPrima> dbMateriaPrima =
        materiaPrimaRepository.findByNombre(materiaPrima.getNombre());

    if (dbMateriaPrima.isPresent()) {
      throw new BadRequestException("Ya existe una materia prima con ese nombre");
    }
  }

  public void validateUpdate(MateriaPrimaPojo materiaPrima) {
    Optional<MateriaPrima> dbMateriaPrima =
        materiaPrimaRepository.findByNombre(materiaPrima.getNombre());

    if (dbMateriaPrima.isPresent() && dbMateriaPrima.get().getId() != materiaPrima.getId()) {
      throw new BadRequestException(
          "Ya existe una materia prima con ese nombre - escoge otro nombre para actualizar");
    }
  }

  public void validateDelete(long materiaPrima) {
    // fixme finish
    // check if ingrediente is been used in any recipe.
  }
}
