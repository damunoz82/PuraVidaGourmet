package org.puravidagourmet.restaurante.domain.enums;

public enum OrdenEstado {
  EN_PROCESO(0),
  TERMINADO(1),
  CANCELADO(2);

  private final int value;

  OrdenEstado(int value) {
    this.value = value;
  }

  public static OrdenEstado getOrdenEstado(int value) {
    for (OrdenEstado estado : OrdenEstado.values()) {
      if (estado.value == value) {
        return estado;
      }
    }
    return null;
  }
}
