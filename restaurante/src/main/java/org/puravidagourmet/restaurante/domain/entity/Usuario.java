package org.puravidagourmet.restaurante.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import lombok.*;
import org.puravidagourmet.restaurante.domain.enums.AuthProvider;
import org.puravidagourmet.restaurante.domain.enums.RoleProvider;

@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {

  private long id;

  private String name;

  private String email;

  @JsonIgnore private String password;

  private AuthProvider provider;

  private String providerId;

  //  @Convert(converter = StringListConverter.class)
  private List<RoleProvider> roles;

  private boolean enabled = false;
}
