package org.puravidagourmet.admin.domain.entity;

import java.sql.Timestamp;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.puravidagourmet.admin.domain.enums.EstadoInventario;

@Data
@ToString
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Inventario {

  private long id;

  private Timestamp fechaCreacion;

  private Timestamp fechaModificacion;

  private String comentario;

  private Departamento departamento;

  private Usuario responsable;

  private Usuario usuarioModifica;

  private String periodoMeta;

  private EstadoInventario estado;

  private List<InventarioDetalle> detalle;

  private float totalValorEnBodega;
}
