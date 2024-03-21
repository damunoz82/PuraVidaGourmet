package org.puravidagourmet.admin.domain.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.sql.Date;
import java.util.List;
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

  @NotBlank(message = "{receta.nombre.notEmpty}")
  private String nombre;

  @Valid private CategoriaRecetaPojo categoria;

  @Min(value = 0, message = "{receta.tamanio.porcion}")
  private long tamanioPorcion;

  @Min(value = 1, message = "{receta.numero.porciones}")
  private long numeroPorciones;

  @Min(value = 1, message = "{receta.temperatura.servido}")
  private long temperaturaDeServido;

  @Min(value = 1, message = "{receta.tiempo.preparacion}")
  private long tiempoPreparacion;

  @Min(value = 0, message = "{receta.tiempo.coccion}")
  private long tiempoCoccion;

  @Min(value = 1, message = "{receta.precio.venta}")
  private float precioDeVenta;

  @Min(value = 0, message = "{receta.impuestos}")
  @Max(value = 1, message = "{receta.impuestos}")
  private float impuestos;

  @NotBlank(message = "{receta.elaboracion}")
  private String elaboracion;

  @NotBlank(message = "{receta.equipo.necesario}")
  private String equipoNecesario;

  @NotBlank(message = "{receta.alergenos}")
  private String alergenos;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private UsuarioPojo usuarioRegistra;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private UsuarioPojo usuarioModifica;

  @NotEmpty(message = "{receta.ingredientes}")
  private List<IngredientePojo> ingredientes;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private float costoReceta;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private float costoPorcion;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private float margenGanancia;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private Date fechaRegistro;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private Date fechaModificacion;
}
