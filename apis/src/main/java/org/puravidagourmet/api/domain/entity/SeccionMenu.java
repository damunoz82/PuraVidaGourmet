package org.puravidagourmet.api.domain.entity;

import java.util.List;
import lombok.*;

@Data
@ToString
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeccionMenu {

  private long seccionId;

  private String nombre;

  private List<ItemMenu> itemMenus;
}
