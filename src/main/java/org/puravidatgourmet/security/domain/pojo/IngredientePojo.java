package org.puravidatgourmet.security.domain.pojo;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IngredientePojo {

  private long ingredienteId;

  @Min(0)
  private long cantidad;

  @NotNull private MateriaPrimaReadOnlyPojo materiaPrima;
}
