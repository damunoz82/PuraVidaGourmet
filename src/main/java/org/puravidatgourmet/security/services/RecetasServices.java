package org.puravidatgourmet.security.services;

import com.google.common.base.Strings;
import java.util.List;
import java.util.Optional;
import org.puravidatgourmet.security.db.repository.IngredienteRepository;
import org.puravidatgourmet.security.db.repository.MateriaPrimaRepository;
import org.puravidatgourmet.security.db.repository.RecetaRepository;
import org.puravidatgourmet.security.domain.entity.Ingrediente;
import org.puravidatgourmet.security.domain.entity.Receta;
import org.puravidatgourmet.security.domain.pojo.IngredientePojo;
import org.puravidatgourmet.security.domain.pojo.RecetaPojo;
import org.puravidatgourmet.security.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RecetasServices {

  @Autowired private RecetaRepository recetaRepository;

  @Autowired private IngredienteRepository ingredienteRepository;

  @Autowired private MateriaPrimaRepository materiaPrimaRepository;

  @Transactional
  public Receta saveReceta(Receta receta) {

    for (Ingrediente ingrediente : receta.getIngredientes()) {
      if (ingrediente.getIngredienteId() <= 0) {
        ingredienteRepository.save(ingrediente);
      }
    }

    return recetaRepository.save(receta);
  }

  public List<Receta> getAll(String categoria) {
    if (!Strings.isNullOrEmpty(categoria)) {
      return recetaRepository.findByCategoriaNombre(categoria);
    }
    return recetaRepository.findAll();
  }

  public Optional<Receta> get(long id) {
    return recetaRepository.findById(id);
  }

  public void delete(long id) {
    recetaRepository.deleteById(id);
  }

  public void validateSave(RecetaPojo receta) {
    Receta dbReceta = recetaRepository.findByNombre(receta.getNombre());

    if (dbReceta != null) {
      throw new BadRequestException("Ya existe una receta con ese nombre");
    }

    validateIngredientes(receta.getIngredientes());
  }

  public void validateUpdate(RecetaPojo receta) {
    Receta dbReceta = recetaRepository.findByNombre(receta.getNombre());

    if (dbReceta != null && dbReceta.getId() != receta.getId()) {
      throw new BadRequestException(
          "Ya existe una receta con ese nombre - escoge otro nombre para actualizar");
    }

    validateIngredientes(receta.getIngredientes());
  }

  public void validateDelete(long id) {
    // fixme finish
    // check if receta is been used in any menu.
  }

  private void validateIngredientes(List<IngredientePojo> ingredientes) {
    ingredientes.forEach(
        i -> {
          materiaPrimaRepository
              .findById(i.getMateriaPrima().getId())
              .orElseThrow(
                  () -> new BadRequestException("Ingrediente con materia prima inexistente."));
        });
  }
}
