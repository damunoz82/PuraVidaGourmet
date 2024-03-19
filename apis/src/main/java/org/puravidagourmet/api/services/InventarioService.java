package org.puravidagourmet.api.services;

import static org.puravidagourmet.api.exceptions.codes.PuraVidaErrorCodes.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.puravidagourmet.api.config.security.UserPrincipal;
import org.puravidagourmet.api.db.repository.InventarioRepository;
import org.puravidagourmet.api.db.repository.UsuarioRepository;
import org.puravidagourmet.api.domain.entity.Inventario;
import org.puravidagourmet.api.domain.entity.InventarioDetalle;
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
    inventario.setEstado(EstadoInventario.EN_PROCESO);
    inventario.setResponsable(
        usuarioRepository
            .findByEmail(userPrincipal.getName())
            .orElseThrow(() -> new PuraVidaExceptionHandler(USU_REC003)));
    return inventarioRepository.iniciarInventario(inventario);
  }

  public void actualizar(Inventario inventario, UserPrincipal userPrincipal) {

    Inventario dbInventario =
        getById(inventario.getId())
            .orElseThrow(() -> new PuraVidaExceptionHandler(INVENT_REC001, inventario.getId()));

    if (dbInventario.getEstado() == EstadoInventario.ABANDONADO
        || dbInventario.getEstado() == EstadoInventario.TERMINADO) {
      throw new PuraVidaExceptionHandler(INVENT_REC002);
    }

    actualizarValorInventario(inventario, dbInventario);

    dbInventario.setUsuarioModifica(
        usuarioRepository
            .findByEmail(userPrincipal.getName())
            .orElseThrow(() -> new PuraVidaExceptionHandler(USU_REC003)));

    inventarioRepository.actualizarInventario(dbInventario);
  }

  public List<Inventario> getAll() {
    return inventarioRepository.findAll();
  }

  public Optional<Inventario> getById(long id) {
    Optional<Inventario> inventario = inventarioRepository.findById(id);
    inventario.ifPresent(value -> value.setDetalle(inventarioRepository.findDetalleByid(id)));
    return inventario;
  }

  public void cambiarEstado(long id, UserPrincipal userPrincipal, EstadoInventario estado) {
    Inventario inventario =
        inventarioRepository
            .findById(id)
            .orElseThrow(() -> new PuraVidaExceptionHandler(INVENT_REC001, id));
    inventario.setUsuarioModifica(
        usuarioRepository
            .findByEmail(userPrincipal.getName())
            .orElseThrow(() -> new PuraVidaExceptionHandler(USU_REC003)));
    inventario.setEstado(estado);

    // fixme - validar que se pueda cambiar estado.
    inventarioRepository.cancel(inventario);
  }

  private void actualizarValorInventario(Inventario inventario, Inventario dbInventario) {
    dbInventario.setComentario(inventario.getComentario());
    dbInventario.setPeriodoMeta(inventario.getPeriodoMeta());

    // set valor por detalle
    Map<Long, Float> idMap =
        inventario.getDetalle().stream()
            .collect(
                Collectors.toMap(
                    InventarioDetalle::getDetalleId, InventarioDetalle::getCantidadEnBodega));
    dbInventario.getDetalle().stream()
        .forEach(
            d -> {
              d.setCantidadEnBodega(idMap.get(d.getDetalleId()));
              d.setValor(
                  idMap.get(d.getDetalleId())
                      * d.getPrecioCompraProducto()
                      / d.getCantidadUnidadProducto());
            });

    // get sum of all.
    dbInventario.setTotalValorEnBodega(
        dbInventario.getDetalle().stream()
            .map(InventarioDetalle::getValor)
            .reduce((float) 0f, Float::sum));
  }
}
