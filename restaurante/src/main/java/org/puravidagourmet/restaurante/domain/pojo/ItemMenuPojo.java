package org.puravidagourmet.restaurante.domain.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@ToString
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemMenuPojo {

  private long itemMenuId;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private String nombreComercial;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private String descripcion;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private float precioVenta;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private String destino;
}
