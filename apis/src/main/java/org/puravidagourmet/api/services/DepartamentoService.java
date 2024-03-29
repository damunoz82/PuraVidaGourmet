package org.puravidagourmet.api.services;

import static org.puravidagourmet.api.exceptions.codes.PuraVidaErrorCodes.DEP_REC001;
import static org.puravidagourmet.api.exceptions.codes.PuraVidaErrorCodes.USU_REC003;

import java.util.List;
import java.util.Optional;
import org.puravidagourmet.api.db.repository.DepartamentoRepository;
import org.puravidagourmet.api.db.repository.UsuarioRepository;
import org.puravidagourmet.api.domain.entity.Departamento;
import org.puravidagourmet.api.exceptions.PuraVidaExceptionHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DepartamentoService {

  private final DepartamentoRepository departamentoRepository;
  private final UsuarioRepository usuarioRepository;

  public DepartamentoService(
      DepartamentoRepository departamentoRepository, UsuarioRepository usuarioRepository) {
    this.departamentoRepository = departamentoRepository;
    this.usuarioRepository = usuarioRepository;
  }

  @Transactional
  public Departamento saveDepartamento(Departamento departamento) {
    if (departamento.getId() <= 0) {
      validateSave(departamento);
    } else {
      validateUpdate(departamento);
    }

    departamento.setResponsable(
        usuarioRepository
            .findByEmail(departamento.getResponsable().getEmail())
            .orElseThrow(
                () ->
                    new PuraVidaExceptionHandler(USU_REC003)));
    return departamentoRepository.save(departamento);
  }

  public Optional<Departamento> getDepartamento(long id) {
    return departamentoRepository.findById(id);
  }

  public List<Departamento> getAll() {
    return departamentoRepository.findAll();
  }

  public void deleteById(long id) {
    validateDelete(id);
    departamentoRepository.delete(id);
  }

  private void validateSave(Departamento departamento) {
    Optional<Departamento> dbTipoProducto =
        departamentoRepository.findByNombre(departamento.getNombre());

    if (dbTipoProducto.isPresent()) {
      throw new PuraVidaExceptionHandler(DEP_REC001);
    }
  }

  private void validateUpdate(Departamento departamento) {
    Optional<Departamento> dbTipoProducto =
        departamentoRepository.findByNombre(departamento.getNombre());

    if (dbTipoProducto.isPresent() && dbTipoProducto.get().getId() != departamento.getId()) {
      throw new PuraVidaExceptionHandler(DEP_REC001);
    }
  }

  private void validateDelete(long id) {
    // fixme - finish
    // check if category is in use.
  }
}
