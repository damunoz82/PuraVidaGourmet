package org.puravidatgourmet.api.domain.pojo;

import jakarta.validation.constraints.Max;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.puravidatgourmet.api.domain.enums.FormatoCompra;
import org.puravidatgourmet.api.domain.enums.UnidadMedidas;

@Data
@ToString
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductoPojo {

  private long id;

  @NotEmpty(message = "Nombre de producto invalido.")
  @Size(min = 0, max = 50, message = "Nombre de producto debe estar entre 0 y 50 caracteres.")
  private String nombre;

  @Size(min = 0, max = 50, message = "Nombre del proveedor debe estar entre 0 y 50 caracteres.")
  private String proveedor;

  @Valid private TipoProductoPojo tipoProducto;

  @NotNull(message = "Unidad de medida invalida.")
  private UnidadMedidas unidadMedida;

  @Min(value = 0, message = "Precio de Compra no debe ser menor que cero.")
  private long precioDeCompra;

  @Min(value = 0, message = "Cantidad por unidad no debe ser menor que cero.")
  private long cantidadPorUnidad;

  @NotNull(message = "Formato de compra invalida.")
  private FormatoCompra formatoCompra;

  @Min(value = 0, message = "Porcentaje de merma no debe ser menor que cero.")
  @Max(value = 1, message = "Porcentaje de merma no debe ser mayor que uno.")
  private float porcentajeMerma;
}
