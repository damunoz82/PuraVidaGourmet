package org.puravidagourmet.restaurante.domain.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import java.sql.Timestamp;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.puravidagourmet.restaurante.domain.enums.OrdenEstado;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrdenPojo {

  private long id;

  @Min(0)
  private long mesaId;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private String nombreMesa;

  private UsuarioPojo mesero;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private Timestamp fechaCreacion;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private OrdenEstado estado;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private float totalNeto;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private float impuestos;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private float total;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private Timestamp fechaFinalizacion;

  @NotEmpty @Valid private List<DetalleOrdenPojo> detalle;
}
