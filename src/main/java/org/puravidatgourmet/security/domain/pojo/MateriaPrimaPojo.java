package org.puravidatgourmet.security.domain.pojo;

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
import org.puravidatgourmet.security.domain.enums.UnidadMedidas;

@Data
@ToString
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MateriaPrimaPojo {

  private long id;

  @NotEmpty (message="Nombre de la materia prima invalido.")
  @Size(min = 0, max = 50, message="Nombre de la materia prima debe estar entre 0 y 50 caracteres.")
  private String nombre;

  @Min(value=0, message="Precio Unitario no debe ser menor que cero.")
  private long precioUnitario;

  @Min(value=0, message="Cantidad por unidad no debe ser menor que cero.")
  private long cantidadPorUnidad;

  @NotNull (message="Unidad de medida invalida.") private UnidadMedidas unidadMedida;

  @Min(value=0, message="Cantidad en bodega no debe ser menor que cero.")
  private long cantidadEnBodega;
}
