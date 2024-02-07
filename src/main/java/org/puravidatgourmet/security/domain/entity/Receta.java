package org.puravidatgourmet.security.domain.entity;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.puravidatgourmet.security.domain.User;

@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "recetas")
public class Receta {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(unique = true)
  private String nombre;

  @OneToMany(fetch = FetchType.EAGER)
  private List<Ingrediente> ingredientes;

  private long tiempoPreparacion;

  private double margenDeBeneficio;

  private double costosFijos;

  private double otrosCostos;

  private double descuentos;

  private double rendimiento;

  @ManyToOne private User usuarioRegistra;

  @OneToOne() private RecetaCategoria categoria;
}
