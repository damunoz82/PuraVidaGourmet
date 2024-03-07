package org.puravidagourmet.api.domain.entity;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.puravidagourmet.api.domain.enums.FormatoCompra;
import org.puravidagourmet.api.domain.enums.UnidadMedidas;

@Data
@ToString
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Producto {

  private long id;

  @NotEmpty
  private String nombre;

  @NotEmpty private String proveedor;

  private TipoProducto tipoProducto;

  @Min(0)
  private long precioDeCompra;

  @Min(0)
  private long cantidadPorUnidad;

  @NotNull private FormatoCompra formatoCompra;

  @NotNull private UnidadMedidas unidadMedida;

  @Min(0)
  private float porcentajeMerma;

  @Min(0)
  private float costeUnitario;
}
