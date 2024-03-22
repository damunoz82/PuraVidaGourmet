package org.puravidagourmet.restaurante.domain.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.sql.Timestamp;
import java.util.List;
import lombok.*;

@Data
@ToString
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuPojo {

  private long id;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private String nombre;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private String temporada;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private String descripcion;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private List<SeccionesMenuPojo> secciones;
}
