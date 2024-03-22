package org.puravidagourmet.restaurante.domain.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.puravidagourmet.restaurante.domain.enums.EstadoDetalleOrden;

@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetalleOrdenPojo {
  private long detalleId;

  private ItemMenuPojo item;

  @Min(1)
  private long cantidad;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private EstadoDetalleOrden estadoDetalleOrden;
}
