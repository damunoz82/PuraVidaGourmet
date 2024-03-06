package org.puravidagourmet.api.services;

import java.util.List;
import java.util.Optional;
import org.puravidagourmet.api.db.repository.CategoriaRecetaRepository;
import org.puravidagourmet.api.domain.entity.CategoriaReceta;
import org.puravidagourmet.api.domain.pojo.CategoriaRecetaPojo;
import org.puravidagourmet.api.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoriaRecetaService {

  @Autowired private CategoriaRecetaRepository categoriaRecetaRepository;

  @Transactional
  public CategoriaReceta saveCategoriaReceta(CategoriaReceta tipoProducto) {
    return categoriaRecetaRepository.save(tipoProducto);
  }

  public Optional<CategoriaReceta> getCategoriaReceta(long id) {
    return categoriaRecetaRepository.findById(id);
  }

  public List<CategoriaReceta> getAll() {
    return categoriaRecetaRepository.findAll();
  }

  public void deleteById(long id) {
    categoriaRecetaRepository.delete(id);
  }

  public void validateSave(CategoriaRecetaPojo categoriaRecetaPojo) {
    Optional<CategoriaReceta> dbTipoProducto =
        categoriaRecetaRepository.findByNombre(categoriaRecetaPojo.getNombre());

    if (dbTipoProducto.isPresent()) {
      throw new BadRequestException("Ya existe una categoria de receta con ese nombre");
    }
  }

  public void validateUpdate(CategoriaRecetaPojo categoriaRecetaPojo) {
    Optional<CategoriaReceta> dbTipoProducto =
        categoriaRecetaRepository.findByNombre(categoriaRecetaPojo.getNombre());

    if (dbTipoProducto.isPresent() && dbTipoProducto.get().getId() != categoriaRecetaPojo.getId()) {
      throw new BadRequestException(
          "Ya existe una categoria de receta con ese nombre - escoge otro nombre para actualizar");
    }
  }

  public void validateDelete(long id) {
    // fixme - finish
    // check if category is in use.
  }
}
