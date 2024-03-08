package org.puravidagourmet.api.domain.enums;

public enum EstadoInventario {
  CREADO(0),
  EN_PROCESO(1),
  TERMINADO(2),
  ABANDONADO(3);

  private final int value;

  EstadoInventario(int value) {
    this.value = value;
  }

  public static EstadoInventario getEstadoInventario(int value) {
    for (EstadoInventario unidad : EstadoInventario.values()) {
      if (unidad.value == value) {
        return unidad;
      }
    }
    return null;
  }
}
