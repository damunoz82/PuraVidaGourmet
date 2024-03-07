package org.puravidagourmet.api.domain.entity;

import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.puravidagourmet.api.domain.User;

@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Departamento {

  private long id;

  @NotEmpty private String nombre;

  @NotEmpty private User responsable;
}
