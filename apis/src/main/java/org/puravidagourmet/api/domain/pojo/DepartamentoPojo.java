package org.puravidagourmet.api.domain.pojo;

import javax.validation.Valid;
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
public class DepartamentoPojo {

  private long id;

  @NotBlank(message = "Nombre del departamento no puede estar en blanco.")
  private String nombre;

  @Valid private UserPojo responsable;
}
