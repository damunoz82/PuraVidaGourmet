package org.puravidagourmet.api.domain.pojo;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

  @NotNull(message = "Nombre del departamento no puede ser nulo.")
  @NotBlank(message = "Nombre del departamento no puede estar en blanco.")
  private String nombre;

  @Valid private UsuarioPojo responsable;
}
