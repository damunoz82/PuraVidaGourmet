package org.puravidagourmet.admin.domain.pojo;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
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

  @NotBlank(message = "{tipo.producto.nombre.notBlank}")
  private String nombre;

  @NotBlank(message = "{tipo.producto.ubicacion.notBlank}")
  private String ubicacion;
}
