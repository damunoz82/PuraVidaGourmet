package org.puravidagourmet.api.domain.converters;

import org.puravidagourmet.api.domain.enums.EstadoInventario;

public class EstadoInventarioConverter {

  public Integer convertToDatabaseColumn(EstadoInventario estadoInventario) {
    return estadoInventario.ordinal();
  }

  public EstadoInventario convertToEntityAttribute(Integer codigo) {
    return EstadoInventario.getEstadoInventario(codigo);
  }
}
