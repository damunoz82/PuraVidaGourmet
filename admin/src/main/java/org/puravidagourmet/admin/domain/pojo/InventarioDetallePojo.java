package org.puravidagourmet.admin.domain.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.ToString;
import org.puravidagourmet.admin.domain.enums.FormatoCompra;
import org.puravidagourmet.admin.domain.enums.UnidadMedidas;

@Data
@ToString
public class InventarioDetallePojo {

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

  @Min(value = 0, message = "{inventario.detalle.cantidadEnBodega}")
  private float cantidadEnBodega;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private float valor;
}
