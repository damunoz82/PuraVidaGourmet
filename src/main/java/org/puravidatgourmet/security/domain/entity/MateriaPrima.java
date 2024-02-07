package org.puravidatgourmet.security.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.puravidatgourmet.security.domain.enums.UnidadMedidas;

@Data
@ToString
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "materiaPrima")
public class MateriaPrima {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @NotEmpty
  @Column(unique = true)
  private String nombre;

  @Min(0)
  private long precioUnitario;

  @Min(0)
  private long cantidadPorUnidad;

  @NotNull private UnidadMedidas unidadMedida;

  @Min(0)
  private long cantidadEnBodega;
}
