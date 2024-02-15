package org.puravidatgourmet.api.services;

import java.util.List;
import java.util.Optional;
import org.puravidatgourmet.api.db.repository.TipoProductoRepository;
import org.puravidatgourmet.api.domain.entity.TipoProducto;
import org.puravidatgourmet.api.domain.pojo.TipoProductoPojo;
import org.puravidatgourmet.api.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TipoProductoService {

  @Autowired private TipoProductoRepository tipoProductoRepository;

  @Transactional
  public TipoProducto saveTipoProducto(TipoProducto tipoProducto) {
    return tipoProductoRepository.save(tipoProducto);
  }

  public Optional<TipoProducto> getTipoProducto(long id) {
    return tipoProductoRepository.findById(id);
  }

  public List<TipoProducto> getAll() {
    return tipoProductoRepository.findAll();
  }

  public void deleteById(long id) {
    tipoProductoRepository.deleteById(id);
  }

  public void validateSave(TipoProductoPojo receta) {
    Optional<TipoProducto> dbTipoProducto = tipoProductoRepository.findByNombre(receta.getNombre());

    if (dbTipoProducto.isPresent()) {
      throw new BadRequestException("Ya existe un tipo de producto con ese nombre");
    }
  }

  public void validateUpdate(TipoProductoPojo receta) {
    Optional<TipoProducto> dbTipoProducto = tipoProductoRepository.findByNombre(receta.getNombre());

    if (dbTipoProducto.isPresent() && dbTipoProducto.get().getId() != receta.getId()) {
      throw new BadRequestException(
          "Ya existe un tipo de producto con ese nombre - escoge otro nombre para actualizar");
    }
  }

  public void validateDelete(long id) {
    // fixme - finish
    // check if category is in use.
  }
}
