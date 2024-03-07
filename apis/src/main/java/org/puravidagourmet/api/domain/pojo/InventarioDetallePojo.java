package org.puravidagourmet.api.domain.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;
import org.puravidagourmet.api.domain.enums.FormatoCompra;
import org.puravidagourmet.api.domain.enums.UnidadMedidas;

@Data
@ToString
public class InventarioDetallePojo {

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private long detalleId;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private String nombreProducto;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private String categoriaProducto;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private String ubicacionProducto;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private FormatoCompra formatoCompraProducto;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private UnidadMedidas unidadMedidaProducto;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private int cantidadUnidadProducto;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private int precioCompraProducto;

  private long cantidadEnBodega;

  private long valor;
}
