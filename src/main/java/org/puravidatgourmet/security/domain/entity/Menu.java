package org.puravidatgourmet.security.domain.entity;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Entity
@Data
public class Menu {

  @Id
  private long id;

  @NotEmpty
  private String nombre;

  @ManyToMany
  private List<Receta> recetas;


}
