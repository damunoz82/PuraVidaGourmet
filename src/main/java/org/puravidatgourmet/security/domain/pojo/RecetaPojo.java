package org.puravidatgourmet.security.domain.pojo;

import java.util.List;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
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
public class RecetaPojo {

  private long id;

  @NotBlank(message = "Nombre de la receta no puede estar en blanco")
  private String nombre;

  @NotEmpty(message = "Lista de ingredientes no puede estar vacia")
  private List<IngredientePojo> ingredientes;

  @Min(0)
  private long tiempoPreparacion;

  @Max(1)
  private double margenDeBeneficio;

  @Min(0)
  private double costosFijos;

  @Min(0)
  private double otrosCostos;

  @Min(0)
  private double descuentos;

  @Min(0)
  private double rendimiento;

  private UserPojo usuarioRegistra;

  @NotNull(message = "Categoria no puede ser nula")
  private RecetaCategoriaPojo categoria;
}
