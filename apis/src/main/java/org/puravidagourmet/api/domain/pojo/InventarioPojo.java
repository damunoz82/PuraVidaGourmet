package org.puravidagourmet.api.domain.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
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

  private String comentario;

  private DepartamentoPojo departamento;

  private UserPojo responsable;

  private UserPojo usuarioModifica;

  private String periodoMeta;

  private EstadoInventario estado;

  private List<InventarioDetallePojo> detalle;
}
