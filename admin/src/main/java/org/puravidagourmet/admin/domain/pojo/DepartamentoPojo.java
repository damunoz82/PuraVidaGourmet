package org.puravidagourmet.admin.domain.pojo;

import jakarta.validation.Valid;
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
public class DepartamentoPojo {

  private long id;

  @NotBlank(message = "{departamento.nombre}")
  private String nombre;

  @Valid private UsuarioPojo responsable;
}
