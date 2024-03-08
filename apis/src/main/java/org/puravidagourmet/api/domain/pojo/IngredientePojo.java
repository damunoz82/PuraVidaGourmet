package org.puravidagourmet.api.domain.pojo;

import jakarta.validation.constraints.Min;
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
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IngredientePojo {

  private long ingredienteId;

  @Min(0)
  private long cantidad;

  @NotNull private ProductoReadOnlyPojo producto;
}
