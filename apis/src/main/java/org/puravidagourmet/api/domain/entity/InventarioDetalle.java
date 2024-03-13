package org.puravidagourmet.api.domain.entity;

import lombok.*;
import org.puravidagourmet.api.domain.enums.FormatoCompra;
import org.puravidagourmet.api.domain.enums.UnidadMedidas;

@Data
@ToString
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventarioDetalle {

  private long detalleId;
  private String nombreProducto;
  private String categoriaProducto;
  private String ubicacionProducto;
  private FormatoCompra formatoCompraProducto;
  private UnidadMedidas unidadMedidaProducto;
  private int cantidadUnidadProducto; // cantidad por unidad de producto.
  private long precioCompraProducto;
  private float cantidadEnBodega;
  private float valor;
}
