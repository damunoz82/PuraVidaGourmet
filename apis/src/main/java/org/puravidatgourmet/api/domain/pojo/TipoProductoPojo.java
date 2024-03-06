package org.puravidatgourmet.api.domain.pojo;

import jakarta.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TipoProductoPojo {

  @Min(0)
  private long id;

  @NotBlank(message = "Nombre de. Tipo de Producto no puede estar en blanco.")
  private String nombre;

  @NotBlank(message = "Ubicacion no puede estar en blanco.")
  private String ubicacion;
}
