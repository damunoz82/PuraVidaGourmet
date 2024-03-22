package org.puravidagourmet.restaurante.domain.entity;

import java.sql.Timestamp;
import java.util.List;
import lombok.*;
import org.puravidagourmet.restaurante.domain.enums.OrdenEstado;

@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Orden {

  private long id;
  private long mesaId;
  private String nombreMesa;
  private Usuario mesero;
  private Timestamp fechaCreacion;
  private OrdenEstado estado;
  private float totalNeto;
  private float impuestos;
  private float total;
  private Timestamp fechaFinalizacion;
  private List<DetalleOrden> detalle;
}
