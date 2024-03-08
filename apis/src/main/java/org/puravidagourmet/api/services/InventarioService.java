package org.puravidagourmet.api.services;

import static org.puravidagourmet.api.exceptions.codes.PuraVidaErrorCodes.INVENT_REC001;
import static org.puravidagourmet.api.exceptions.codes.PuraVidaErrorCodes.USU_REC003;

import java.util.List;
import java.util.Optional;
import org.puravidagourmet.api.config.security.UserPrincipal;
import org.puravidagourmet.api.db.repository.InventarioRepository;
import org.puravidagourmet.api.db.repository.UsuarioRepository;
import org.puravidagourmet.api.domain.entity.Inventario;
import org.puravidagourmet.api.domain.enums.EstadoInventario;
import org.puravidagourmet.api.exceptions.PuraVidaExceptionHandler;
import org.springframework.stereotype.Service;

@Service
public class InventarioService {

  private final UsuarioRepository usuarioRepository;
  private final InventarioRepository inventarioRepository;

  public InventarioService(
      UsuarioRepository usuarioRepository, InventarioRepository inventarioRepository) {
    this.usuarioRepository = usuarioRepository;
    this.inventarioRepository = inventarioRepository;
  }

  public Inventario createInventario(Inventario inventario, UserPrincipal userPrincipal) {
    inventario.setEstado(EstadoInventario.CREADO);
    inventario.setResponsable(
        usuarioRepository
            .findByEmail(userPrincipal.getName()) // fixme - check that user is active
            .orElseThrow(() -> new PuraVidaExceptionHandler(USU_REC003)));
    return inventarioRepository.iniciarInventario(inventario);
  }

  public List<Inventario> getAll() {
    return inventarioRepository.findAll();
  }

  public Optional<Inventario> getById(long id) {
    Optional<Inventario> inventario = inventarioRepository.findById(id);
    inventario.ifPresent(value -> value.setDetalle(inventarioRepository.findDetalleByid(id)));
    return inventario;
  }

  public void cancel(long id, UserPrincipal userPrincipal) {
    Inventario inventario =
        inventarioRepository
            .findById(id)
            .orElseThrow(() -> new PuraVidaExceptionHandler(INVENT_REC001, id));
    inventario.setUsuarioModifica(
        usuarioRepository
            .findByEmail(userPrincipal.getName()) // fixme check that user is active.
            .orElseThrow(() -> new PuraVidaExceptionHandler(USU_REC003)));
    inventario.setEstado(EstadoInventario.ABANDONADO);
    inventarioRepository.cancel(inventario);
  }
}
