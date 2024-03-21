package org.puravidagourmet.admin.domain.pojo;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@ToString
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeccionesMenuPojo {

  private long seccionId;

  @NotBlank(message = "{menu.seccion.nombre.notBlank}")
  @Size(min = 0, max = 50, message = "{menu.seccion.nombre.size}")
  private String nombre;

  @NotEmpty private List<ItemMenuPojo> itemMenus;
}
