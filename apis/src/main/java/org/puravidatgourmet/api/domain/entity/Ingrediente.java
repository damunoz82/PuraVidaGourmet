package org.puravidatgourmet.api.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
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
