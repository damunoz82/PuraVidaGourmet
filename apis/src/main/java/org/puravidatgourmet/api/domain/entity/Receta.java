package org.puravidatgourmet.api.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.puravidatgourmet.api.domain.User;

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
