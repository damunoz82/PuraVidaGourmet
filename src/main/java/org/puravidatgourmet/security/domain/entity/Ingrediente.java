package org.puravidatgourmet.security.domain.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
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
@Entity
@Table(name = "ingrediente")
public class Ingrediente {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long ingredienteId;

  @OneToOne
  private MateriaPrima materiaPrima;

  private long cantidad;
}
