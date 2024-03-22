package org.puravidagourmet.restaurante.payload;

import java.util.List;
import lombok.*;
import org.puravidagourmet.restaurante.domain.entity.DetalleOrden;

@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrdenPayload {

  private String header;
  private List<DetalleOrden> details;
}
