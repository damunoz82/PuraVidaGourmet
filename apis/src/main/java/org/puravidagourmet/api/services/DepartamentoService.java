package org.puravidagourmet.api.services;

import java.util.List;
import java.util.Optional;
import org.puravidagourmet.api.db.repository.DepartamentoRepository;
import org.puravidagourmet.api.db.repository.UsuarioRepository;
import org.puravidagourmet.api.domain.entity.Departamento;
import org.puravidagourmet.api.domain.pojo.DepartamentoPojo;
import org.puravidagourmet.api.exceptions.BadRequestException;
import org.puravidagourmet.api.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DepartamentoService {

  @Autowired private DepartamentoRepository departamentoRepository;
  @Autowired private UsuarioRepository usuarioRepository;

  @Transactional
  public Departamento saveDepartamento(Departamento departamento) {
    departamento.setResponsable(
        usuarioRepository
            .findByEmail(departamento.getResponsable().getEmail())
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        "Usuario", "email", departamento.getResponsable().getEmail())));
    return departamentoRepository.save(departamento);
  }

  public Optional<Departamento> getDepartamento(long id) {
    return departamentoRepository.findById(id);
  }

  public List<Departamento> getAll() {
    return departamentoRepository.findAll();
  }

  public void deleteById(long id) {
    departamentoRepository.delete(id);
  }

  public void validateSave(DepartamentoPojo departamento) {
    Optional<Departamento> dbTipoProducto =
        departamentoRepository.findByNombre(departamento.getNombre());

    if (dbTipoProducto.isPresent()) {
      throw new BadRequestException("Ya existe un tipo de producto con ese nombre");
    }
  }

  public void validateUpdate(DepartamentoPojo departamento) {
    Optional<Departamento> dbTipoProducto =
        departamentoRepository.findByNombre(departamento.getNombre());

    if (dbTipoProducto.isPresent() && dbTipoProducto.get().getId() != departamento.getId()) {
      throw new BadRequestException(
          "Ya existe un departamento con ese nombre - escoge otro nombre para actualizar");
    }
  }

  public void validateDelete(long id) {
    // fixme - finish
    // check if category is in use.
  }
}
