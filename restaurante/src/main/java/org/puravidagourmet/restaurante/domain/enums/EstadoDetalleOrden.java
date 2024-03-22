package org.puravidagourmet.restaurante.domain.enums;

public enum EstadoDetalleOrden {
  RECIBIDA(0),
  ACEPTADA(1),
  RECHAZADA(2),
  FINALIZA(2),
  CANCELADA(4);

  private final int value;

  EstadoDetalleOrden(int value) {
    this.value = value;
  }

  public static EstadoDetalleOrden getEstadoDetalleOrden(int value) {
    for (EstadoDetalleOrden estado : EstadoDetalleOrden.values()) {
      if (estado.value == value) {
        return estado;
      }
    }
    return null;
  }
}
