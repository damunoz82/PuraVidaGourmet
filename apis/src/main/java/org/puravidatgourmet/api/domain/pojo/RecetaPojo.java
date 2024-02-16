package org.puravidatgourmet.api.domain.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.sql.Date;
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

  @NotNull(message = "Categoria no puede ser nula")
  private CategoriaRecetaPojo categoria;

  @Min(0)
  private long tamanioPorcion;

  @Max(0)
  private long numeroPorciones;

  @Min(0)
  private long temperaturaDeServido;

  @Min(0)
  private long tiempoPreparacion;

  @Min(0)
  private long tiempoCoccion;

  @Min(0)
  private double precioDeVenta;

  @Min(0)
  @Max(1)
  private double impuestos;

  @NotEmpty private String elaboracion;

  @NotEmpty private String equipoNecesario;

  @NotEmpty private String alergenos;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private UserPojo usuarioRegistra;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private UserPojo usuarioModifica;

  @NotEmpty(message = "Lista de ingredientes no puede estar vacia")
  private List<IngredientePojo> ingredientes;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private double costoReceta;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private double costoPorcion;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private double margenGanancia;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private Date fechaRegistro;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private Date fechaModificacion;
}
