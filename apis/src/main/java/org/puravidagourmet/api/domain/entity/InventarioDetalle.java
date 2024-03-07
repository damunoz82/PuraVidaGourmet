package org.puravidagourmet.api.domain.entity;

import lombok.*;
import org.puravidagourmet.api.domain.converters.FormatoCompraConverter;
import org.puravidagourmet.api.domain.converters.UnidadMedidaConverter;
import org.puravidagourmet.api.domain.enums.FormatoCompra;
import org.puravidagourmet.api.domain.enums.UnidadMedidas;

@Data
@NoArgsConstructor
public class InventarioDetalle {

  private long detalleId;
  private String nombreProducto;
  private String categoriaProducto;
  private String ubicacionProducto;
//  @Convert(converter = FormatoCompraConverter.class)
  private FormatoCompra formatoCompraProducto;
//  @Convert(converter = UnidadMedidaConverter.class)
  private UnidadMedidas unidadMedidaProducto;
  private int cantidadUnidadProducto;
  private int precioCompraProducto;
  private long cantidadEnBodega;
  private long valor;
}
