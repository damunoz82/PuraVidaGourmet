package org.puravidagourmet.api.services;

import java.util.List;
import java.util.Optional;
import org.puravidagourmet.api.db.repository.CategoriaRecetaRepository;
import org.puravidagourmet.api.domain.entity.CategoriaReceta;
import org.puravidagourmet.api.exceptions.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoriaRecetaService {

  private final CategoriaRecetaRepository categoriaRecetaRepository;

  public CategoriaRecetaService(CategoriaRecetaRepository categoriaRecetaRepository) {
    this.categoriaRecetaRepository = categoriaRecetaRepository;
  }

  @Transactional
  public CategoriaReceta saveCategoriaReceta(CategoriaReceta categoriaReceta) {
    if (categoriaReceta.getId() <= 0) {
      validateSave(categoriaReceta);
    } else {
      validateUpdate(categoriaReceta);
    }
    return categoriaRecetaRepository.save(categoriaReceta);
  }

  public Optional<CategoriaReceta> getCategoriaReceta(long id) {
    return categoriaRecetaRepository.findById(id);
  }

  public List<CategoriaReceta> getAll() {
    return categoriaRecetaRepository.findAll();
  }

  public void deleteById(long id) {
    validateDelete(id);
    categoriaRecetaRepository.delete(id);
  }

  private void validateSave(CategoriaReceta categoriaRecetaPojo) {
    Optional<CategoriaReceta> dbTipoProducto =
        categoriaRecetaRepository.findByNombre(categoriaRecetaPojo.getNombre());

    if (dbTipoProducto.isPresent()) {
      throw new BadRequestException("Ya existe una categoria de receta con ese nombre");
    }
  }

  private void validateUpdate(CategoriaReceta categoriaRecetaPojo) {
    Optional<CategoriaReceta> dbTipoProducto =
        categoriaRecetaRepository.findByNombre(categoriaRecetaPojo.getNombre());

    if (dbTipoProducto.isPresent() && dbTipoProducto.get().getId() != categoriaRecetaPojo.getId()) {
      throw new BadRequestException(
          "Ya existe una categoria de receta con ese nombre - escoge otro nombre para actualizar");
    }
  }

  private void validateDelete(long id) {
    // fixme - finish
    // check if category is in use.
  }
}
