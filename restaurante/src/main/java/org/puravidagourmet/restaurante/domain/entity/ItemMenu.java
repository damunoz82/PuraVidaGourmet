package org.puravidagourmet.restaurante.domain.entity;

import lombok.*;

@Data
@ToString
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemMenu {

  private long itemMenuId;
  private long recetaId;
  private String nombreComercial;
  private String descripcion;
  private float precioVenta;
  private String destino;
}
