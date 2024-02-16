package org.puravidatgourmet.api.domain.pojo;

import javax.validation.constraints.Size;
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
public class ProductoReadOnlyPojo {

  private long id;

  @Size(
      min = 0,
      max = 50,
      message = "Nombre de la materia prima debe estar entre 0 y 50 caracteres.")
  private String nombre;
}
