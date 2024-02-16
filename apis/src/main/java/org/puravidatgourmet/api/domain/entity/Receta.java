package org.puravidatgourmet.api.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.sql.Date;
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

  @OneToOne() private CategoriaReceta categoriaReceta;

  private long tamanioPorcion;

  private long numeroPorciones;

  private long temperaturaDeServido;

  private long tiempoPreparacion;

  private long tiempoCoccion;

  private double precioDeVenta;

  private double impuestos;

  private String elaboracion;

  private String equipoNecesario;

  private String alergenos;

  @JoinColumn(updatable = false)
  @ManyToOne
  private User usuarioRegistra;

  @ManyToOne private User usuarioModifica;

  @OneToMany(fetch = FetchType.EAGER, targetEntity = Ingrediente.class)
  private List<Ingrediente> ingredientes;

  private double costoReceta;

  private double costoPorcion;

  private double margenGanancia;

  private Date fechaRegistro;

  private Date fechaModificacion;
}
