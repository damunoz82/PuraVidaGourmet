package org.puravidagourmet.admin.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.puravidagourmet.admin.domain.enums.FormatoCompra;
import org.puravidagourmet.admin.domain.enums.UnidadMedidas;

@Data
@ToString
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Producto {

  private long id;

  private String nombre;

  private String proveedor;

  private TipoProducto tipoProducto;

  private long precioDeCompra;

  private long cantidadPorUnidad;

  private FormatoCompra formatoCompra;

  private UnidadMedidas unidadMedida;

  private float porcentajeMerma;

  private float costeUnitario;
}
