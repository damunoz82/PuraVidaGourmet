package org.puravidatgourmet.api.services;

import java.util.List;
import java.util.Optional;
import org.puravidatgourmet.api.db.repository.RecetaCategoriaRepository;
import org.puravidatgourmet.api.domain.entity.RecetaCategoria;
import org.puravidatgourmet.api.domain.pojo.RecetaCategoriaPojo;
import org.puravidatgourmet.api.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RecetaCategoriaService {

  @Autowired private RecetaCategoriaRepository categoriaRepository;

  @Transactional
  public RecetaCategoria saveRecetaCategoria(RecetaCategoria categoria) {
    return categoriaRepository.save(categoria);
  }

  public Optional<RecetaCategoria> getRecetaCategoria(long id) {
    return categoriaRepository.findById(id);
  }

  public List<RecetaCategoria> getAll() {
    return categoriaRepository.findAll();
  }

  public void deleteById(long id) {
    categoriaRepository.deleteById(id);
  }

  public void validateSave(RecetaCategoriaPojo receta) {
    RecetaCategoria dbReceta = categoriaRepository.findByNombre(receta.getNombre());

    if (dbReceta != null) {
      throw new BadRequestException("Ya existe una categoria con ese nombre");
    }
  }

  public void validateUpdate(RecetaCategoriaPojo receta) {
    RecetaCategoria dbReceta = categoriaRepository.findByNombre(receta.getNombre());

    if (dbReceta != null && dbReceta.getId() != receta.getId()) {
      throw new BadRequestException(
          "Ya existe una categoria con ese nombre - escoge otro nombre para actualizar");
    }
  }

  public void validateDelete(long id) {
    // fixme - finish
    // check if category is in use.
  }
}
