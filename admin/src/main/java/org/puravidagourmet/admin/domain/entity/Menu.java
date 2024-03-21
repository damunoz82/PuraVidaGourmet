package org.puravidagourmet.admin.domain.entity;

import java.sql.Timestamp;
import java.util.List;
import lombok.*;

@Data
@ToString
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Menu {

  private long id;
  private String nombre;
  private String temporada;
  private String descripcion;
  private Usuario usuarioRegistra;
  private Timestamp fechaCreacion;
  private Usuario ususarioModifica;
  private Timestamp fechaModificacion;
  private boolean menuEstado;

  private List<SeccionMenu> secciones;
}
