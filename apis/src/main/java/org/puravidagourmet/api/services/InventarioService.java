package org.puravidagourmet.api.services;

import java.util.List;
import java.util.Optional;
import org.puravidagourmet.api.config.security.UserPrincipal;
import org.puravidagourmet.api.db.repository.InventarioRepository;
import org.puravidagourmet.api.db.repository.UsuarioRepository;
import org.puravidagourmet.api.domain.entity.Inventario;
import org.puravidagourmet.api.domain.enums.EstadoInventario;
import org.puravidagourmet.api.exceptions.BadRequestException;
import org.puravidagourmet.api.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
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
            .findByEmail(userPrincipal.getName())
            .orElseThrow(() -> new BadRequestException("Usuario que registra no fue encontrado")));
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
            .orElseThrow(() -> new ResourceNotFoundException("Inventario", "id", id));
    inventario.setUsuarioModifica(
        usuarioRepository
            .findByEmail(userPrincipal.getName())
            .orElseThrow(() -> new BadRequestException("Usuario Modifica no encontrado.")));
    inventario.setEstado(EstadoInventario.ABANDONADO);
    inventarioRepository.cancel(inventario);
  }
}
