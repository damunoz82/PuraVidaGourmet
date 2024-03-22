package org.puravidagourmet.restaurante.domain.entity;

import lombok.*;
import org.puravidagourmet.restaurante.domain.enums.EstadoDetalleOrden;

@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetalleOrden {

  private long detalleId;
  private ItemMenu item;
  private long cantidad;
  private EstadoDetalleOrden estadoDetalleOrden;
}
