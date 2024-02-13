package org.puravidatgourmet.api.domain.pojo;

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
public class RecetaCategoriaPojo {

  private long id;

  @NotBlank(message = "Nombre de la categoria no puede estar en blanco.")
  private String nombre;
}
