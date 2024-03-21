package org.puravidagourmet.admin.services;

import java.util.List;
import java.util.Optional;
import org.puravidagourmet.admin.db.repository.CategoriaRecetaRepository;
import org.puravidagourmet.admin.domain.entity.CategoriaReceta;
import org.puravidagourmet.admin.exceptions.PuraVidaExceptionHandler;
import org.puravidagourmet.admin.exceptions.codes.PuraVidaErrorCodes;
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
      throw new PuraVidaExceptionHandler(PuraVidaErrorCodes.CAT_REC001);
    }
  }

  private void validateUpdate(CategoriaReceta categoriaRecetaPojo) {
    Optional<CategoriaReceta> dbTipoProducto =
        categoriaRecetaRepository.findByNombre(categoriaRecetaPojo.getNombre());

    if (dbTipoProducto.isPresent() && dbTipoProducto.get().getId() != categoriaRecetaPojo.getId()) {
      throw new PuraVidaExceptionHandler(PuraVidaErrorCodes.CAT_REC001);
    }
  }

  private void validateDelete(long id) {
    // fixme - finish
    // check if category is in use.
  }
}
