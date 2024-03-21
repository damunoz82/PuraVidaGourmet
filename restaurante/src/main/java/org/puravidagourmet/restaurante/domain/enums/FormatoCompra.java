package org.puravidagourmet.restaurante.domain.enums;

public enum FormatoCompra {
  PAQUETE(0),
  ROLLO(1),
  BOTELLA(2),
  OTRO(3);

  private final int value;

  FormatoCompra(int value) {
    this.value = value;
  }

  public static FormatoCompra getFormatoCompra(int value) {
    for (FormatoCompra unidad : FormatoCompra.values()) {
      if (unidad.value == value) {
        return unidad;
      }
    }
    return null;
  }
}
