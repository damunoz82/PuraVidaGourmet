package org.puravidagourmet.api.domain.entity;

import java.sql.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.puravidagourmet.api.domain.enums.EstadoInventario;

@Data
@ToString
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Inventario {

  private long id;

  private Date fecha_creacion;

  private Date fecha_modificacion;

  private String comentario;

  private Departamento departamento;

  private Usuario responsable;

  private Usuario usuarioModifica;

  private String periodoMeta;

  private EstadoInventario estado;

  private List<InventarioDetalle> detalle;

  private float totalValorEnBodega;
}
