package org.puravidatgourmet.api.domain.entity;

import java.sql.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.puravidatgourmet.api.domain.User;
import org.puravidatgourmet.api.domain.converters.EstadoInventarioConverter;
import org.puravidatgourmet.api.domain.enums.EstadoInventario;

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

  private User responsable;

  private String periodoMeta;

//  @Convert(converter = EstadoInventarioConverter.class)
  private EstadoInventario estado;

  private List<InventarioDetalle> detalle;
}
