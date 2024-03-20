package org.puravidagourmet.api.domain.pojo;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MesaPojo {

  private long id;

  @NotBlank(message = "{mesa.nombre}")
  private String nombre;

  @Min(value = 1, message = "{mesa.capacidad}")
  @Max(value = 10, message = "{mesa.capacidad}")
  private int capacidad;
}
