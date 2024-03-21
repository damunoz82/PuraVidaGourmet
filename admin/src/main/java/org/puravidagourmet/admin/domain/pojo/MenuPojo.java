package org.puravidagourmet.admin.domain.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
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

  @NotBlank(message = "{menu.nombre.notBlank}")
  @Size(min = 0, max = 50, message = "{menu.nombre.size}")
  private String nombre;

  @NotBlank(message = "{menu.temporada.notBlank}")
  @Size(min = 0, max = 50, message = "{menu.temporada.size}")
  private String temporada;

  @NotBlank(message = "{menu.descripcion.notBlank}")
  @Size(min = 0, max = 250, message = "{menu.descripcion.size}")
  private String descripcion;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private UsuarioPojo usuarioRegistra;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private Timestamp fechaCreacion;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private UsuarioPojo ususarioModifica;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private Timestamp fechaModificacion;

  @NotEmpty private List<SeccionesMenuPojo> secciones;

  private boolean menuEstado;
}
