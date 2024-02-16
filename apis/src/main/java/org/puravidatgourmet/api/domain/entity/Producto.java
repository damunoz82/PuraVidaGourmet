package org.puravidatgourmet.api.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.puravidatgourmet.api.domain.enums.FormatoCompra;
import org.puravidatgourmet.api.domain.enums.UnidadMedidas;

@Data
@ToString
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "producto")
public class Producto {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @NotEmpty
  @Column(unique = true)
  private String nombre;

  @NotEmpty private String proveedor;

  @ManyToOne private TipoProducto tipoProducto;

  @Min(0)
  private long precioDeCompra;

  @Min(0)
  private long cantidadPorUnidad;

  @NotNull private FormatoCompra formatoCompra;

  @NotNull private UnidadMedidas unidadMedida;

  @Min(0)
  private float porcentajeMerma;

  @Min(0)
  private float costeUnitario;
}
