package org.puravidatgourmet.api.domain.entity;

import jakarta.persistence.*;

import javax.validation.constraints.NotEmpty;
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
@Table(name = "departamento")
public class Departamento {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @NotEmpty private String nombre;

  @ManyToOne
  @NotEmpty private User responsable;
}
