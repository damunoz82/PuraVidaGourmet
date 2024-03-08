package org.puravidagourmet.api.domain.entity;

import java.sql.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Receta {

  private long id;

  private String nombre;

  private CategoriaReceta categoriaReceta;

  private long tamanioPorcion;

  private long numeroPorciones;

  private long temperaturaDeServido;

  private long tiempoPreparacion;

  private long tiempoCoccion;

  private float precioDeVenta;

  private float impuestos;

  private String elaboracion;

  private String equipoNecesario;

  private String alergenos;

  private Usuario usuarioRegistra;

  private Usuario usuarioModifica;

  private List<Ingrediente> ingredientes;

  private float costoReceta;

  private float costoPorcion;

  private float margenGanancia;

  private Date fechaRegistro;

  private Date fechaModificacion;
}
