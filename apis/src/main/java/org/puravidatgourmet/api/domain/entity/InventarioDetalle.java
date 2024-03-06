package org.puravidatgourmet.api.domain.entity;

import lombok.*;
import org.puravidatgourmet.api.domain.converters.FormatoCompraConverter;
import org.puravidatgourmet.api.domain.converters.UnidadMedidaConverter;
import org.puravidatgourmet.api.domain.enums.FormatoCompra;
import org.puravidatgourmet.api.domain.enums.UnidadMedidas;

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
