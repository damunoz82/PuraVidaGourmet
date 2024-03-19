package org.puravidagourmet.api.domain.pojo;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@ToString
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemMenuPojo {

  private long itemMenuId;
  private long recetaId;

  @NotBlank(message = "{menu.item.nombre.notBlank}")
  @Size(min = 0, max = 50, message = "{menu.item.nombre.size}")
  private String nombreComercial;

  @NotBlank(message = "{menu.item.descripcion.notBlank}")
  @Size(min = 0, max = 250, message = "{menu.item.descripcion.size}")
  private String descripcion;

  @Min(value = 0, message = "{menu.item.precio}")
  private float precioVenta;
}
