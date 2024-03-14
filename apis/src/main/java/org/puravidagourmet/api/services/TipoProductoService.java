package org.puravidagourmet.api.services;

import static org.puravidagourmet.api.exceptions.codes.PuraVidaErrorCodes.TPROD_REC001;

import java.util.List;
import java.util.Optional;
import org.puravidagourmet.api.db.repository.TipoProductoRepository;
import org.puravidagourmet.api.domain.entity.TipoProducto;
import org.puravidagourmet.api.exceptions.PuraVidaExceptionHandler;
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
      throw new PuraVidaExceptionHandler(TPROD_REC001);
    }
  }

  private void validateUpdate(TipoProducto receta) {
    Optional<TipoProducto> dbTipoProducto = tipoProductoRepository.findByNombre(receta.getNombre());

    if (dbTipoProducto.isPresent() && dbTipoProducto.get().getId() != receta.getId()) {
      throw new PuraVidaExceptionHandler(TPROD_REC001);
    }
  }

  private void validateDelete(long id) {
    // fixme - finish
    // check if category is in use.
  }
}
