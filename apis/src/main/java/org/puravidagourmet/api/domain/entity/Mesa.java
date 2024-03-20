package org.puravidagourmet.api.domain.entity;

import lombok.*;

@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Mesa {

  private long id;

  private String nombre;

  private int capacidad;
}
