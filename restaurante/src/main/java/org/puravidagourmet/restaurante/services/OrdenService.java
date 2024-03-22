package org.puravidagourmet.restaurante.services;

import static org.puravidagourmet.restaurante.exceptions.codes.PuraVidaErrorCodes.ORD_001;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.puravidagourmet.restaurante.config.security.UserPrincipal;
import org.puravidagourmet.restaurante.db.repository.MenuRepository;
import org.puravidagourmet.restaurante.db.repository.OrdenRepository;
import org.puravidagourmet.restaurante.db.repository.UsuarioRepository;
import org.puravidagourmet.restaurante.domain.entity.DetalleOrden;
import org.puravidagourmet.restaurante.domain.entity.Orden;
import org.puravidagourmet.restaurante.domain.enums.EstadoDetalleOrden;
import org.puravidagourmet.restaurante.domain.enums.OrdenEstado;
import org.puravidagourmet.restaurante.exceptions.PuraVidaExceptionHandler;
import org.puravidagourmet.restaurante.exceptions.codes.PuraVidaErrorCodes;
import org.puravidagourmet.restaurante.payload.OrdenPayload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrdenService {

  private final OrdenRepository ordenRepository;
  private final UsuarioRepository usuarioRepository;
  private final MenuRepository menuRepository;
  private final SimpMessagingTemplate template;

  public OrdenService(
      OrdenRepository ordenRepository,
      UsuarioRepository usuarioRepository,
      MenuRepository menuRepository,
      SimpMessagingTemplate template) {
    this.ordenRepository = ordenRepository;
    this.usuarioRepository = usuarioRepository;
    this.menuRepository = menuRepository;
    this.template = template;
  }

  public Orden save(Orden orden, UserPrincipal user) {
    if (orden.getId() <= 0) {
      // creating a new order.
      // set mesero.
      orden.setMesero(
          usuarioRepository
              .findByEmail(user.getName())
              .orElseThrow(() -> new PuraVidaExceptionHandler(PuraVidaErrorCodes.USU_REC003)));

      // set estado de la orden
      orden.setEstado(OrdenEstado.EN_PROCESO);
    }

    // set estado de cada uno de los detalles
    orden.getDetalle().forEach(d -> d.setEstadoDetalleOrden(EstadoDetalleOrden.RECIBIDA));

    // perform validations here and wether to save or update.
    validateDetalle(orden);

    orden = ordenRepository.save(orden);

    //  transfer to corresponding UI
    notifyDepartment(orden);

    return orden;
  }

  public void terminar(long id, UserPrincipal user) {
    Orden orden =
        ordenRepository.findById(id).orElseThrow(() -> new PuraVidaExceptionHandler(ORD_001, id));

    // validar estado de los pedidos?  o lo dejamos pasar?

    calcularMontoActual(orden);
    orden.setEstado(OrdenEstado.TERMINADO);

    ordenRepository.terminar(orden);
  }

  public List<Orden> findAll() {
    return ordenRepository.findall();
  }

  public Optional<Orden> findById(long id) {
    Optional<Orden> orden = ordenRepository.findById(id);
    if (orden.isPresent()) {
      return Optional.of(calcularMontoActual(orden.get()));
    }
    return orden;
  }

  private Orden calcularMontoActual(Orden orden) {
    double total =
        orden.getDetalle().stream()
            .mapToDouble(d -> d.getItem().getPrecioVenta() * d.getCantidad())
            .sum();
    orden.setTotal((float) total);
    orden.setImpuestos(orden.getTotal() * 0.23f); // fixme - tomar este valor de algun configurable.
    orden.setTotalNeto(orden.getTotal() - orden.getImpuestos());
    return orden;
  }

  private void validateDetalle(Orden orden) {
    orden
        .getDetalle()
        .forEach(
            d ->
                menuRepository
                    .findItemMenuById(d.getItem().getItemMenuId())
                    .orElseThrow(() -> new PuraVidaExceptionHandler(PuraVidaErrorCodes.ORD_002)));
  }

  private void notifyDepartment(Orden orden) {
    // bar
    List<DetalleOrden> detalleBar =
        orden.getDetalle().stream()
            .filter(d -> d.getItem().getDestino().equals("Bar"))
            .collect(Collectors.toList());

    OrdenPayload barPayLoad =
        OrdenPayload.builder().header(generateHeader(orden)).details(detalleBar).build();
    this.template.convertAndSend("/restaurante-topic/bar", barPayLoad);

    // cocina
    List<DetalleOrden> detalleCocina =
        orden.getDetalle().stream()
            .filter(d -> d.getItem().getDestino().equals("Cocina"))
            .collect(Collectors.toList());

    OrdenPayload cocinaPayLoad =
        OrdenPayload.builder().header(generateHeader(orden)).details(detalleCocina).build();
    this.template.convertAndSend("/restaurante-topic/cocina", cocinaPayLoad);
  }

  private String generateHeader(Orden orden) {
    return "Orden #" + orden.getId() + " / Mesa #" + orden.getMesaId();
  }
}
