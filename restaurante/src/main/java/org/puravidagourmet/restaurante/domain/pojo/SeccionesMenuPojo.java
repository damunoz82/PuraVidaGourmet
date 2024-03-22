package org.puravidagourmet.restaurante.domain.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.*;

@Data
@ToString
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeccionesMenuPojo {

  private long seccionId;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private String nombre;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private List<ItemMenuPojo> itemMenus;
}
