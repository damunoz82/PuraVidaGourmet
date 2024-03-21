package org.puravidagourmet.restaurante.domain.enums;

public enum UnidadMedidas {
  GRAMOS(0),
  MILI_LITROS(1),
  UNIDAD(2);

  private final int value;

  UnidadMedidas(int value) {
    this.value = value;
  }

  public static UnidadMedidas getUnidadMedida(int value) {
    for (UnidadMedidas unidad : UnidadMedidas.values()) {
      if (unidad.value == value) {
        return unidad;
      }
    }
    return null;
  }
}
