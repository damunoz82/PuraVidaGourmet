package org.puravidatgourmet.api.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Entity
@Data
public class Menu {

  @Id private long id;

  @NotEmpty private String nombre;

  @ManyToMany private List<Receta> recetas;
}
