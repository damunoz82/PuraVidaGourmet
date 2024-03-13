package org.puravidagourmet.api.domain.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.sql.Date;
import java.util.List;
import lombok.Data;
import lombok.ToString;
import org.puravidagourmet.api.domain.enums.EstadoInventario;

@Data
@ToString
public class InventarioPojo {

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private long id;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private Date fecha_creacion;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private Date fecha_modificacion;

  @NotBlank(message = "{inventario.comentario.notBlank}")
  private String comentario;

  @NotNull private DepartamentoPojo departamento;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private UsuarioPojo responsable;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private UsuarioPojo usuarioModifica;

  @NotBlank(message = "{inventario.periodoMeta.notBlank}")
  private String periodoMeta;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private EstadoInventario estado;

  @NotNull private List<InventarioDetallePojo> detalle;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private float totalValorEnBodega;
}
