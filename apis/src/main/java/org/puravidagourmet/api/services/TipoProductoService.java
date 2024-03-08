package org.puravidagourmet.api.services;

import java.util.List;
import java.util.Optional;
import org.puravidagourmet.api.db.repository.TipoProductoRepository;
import org.puravidagourmet.api.domain.entity.TipoProducto;
import org.puravidagourmet.api.exceptions.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TipoProductoService {

  private final TipoProductoRepository tipoProductoRepository;

  public TipoProductoService(TipoProductoRepository tipoProductoRepository) {
    this.tipoProductoRepository = tipoProductoRepository;
  }

  @Transactional
  public TipoProducto saveTipoProducto(TipoProducto tipoProducto) {
    if (tipoProducto.getId() <= 0) {
      validateSave(tipoProducto);
    } else {
      validateUpdate(tipoProducto);
    }
    return tipoProductoRepository.save(tipoProducto);
  }

  public Optional<TipoProducto> getTipoProducto(long id) {
    return tipoProductoRepository.findById(id);
  }

  public List<TipoProducto> getAll() {
    return tipoProductoRepository.findAll();
  }

  public void deleteById(long id) {
    validateDelete(id);
    tipoProductoRepository.delete(id);
  }

  private void validateSave(TipoProducto receta) {
    Optional<TipoProducto> dbTipoProducto = tipoProductoRepository.findByNombre(receta.getNombre());

    if (dbTipoProducto.isPresent()) {
      throw new BadRequestException("Ya existe un tipo de producto con ese nombre");
    }
  }

  private void validateUpdate(TipoProducto receta) {
    Optional<TipoProducto> dbTipoProducto = tipoProductoRepository.findByNombre(receta.getNombre());

    if (dbTipoProducto.isPresent() && dbTipoProducto.get().getId() != receta.getId()) {
      throw new BadRequestException(
          "Ya existe un tipo de producto con ese nombre - escoge otro nombre para actualizar");
    }
  }

  private void validateDelete(long id) {
    // fixme - finish
    // check if category is in use.
  }
}
