package org.puravidagourmet.api.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import lombok.*;
import org.puravidagourmet.api.domain.enums.AuthProvider;
import org.puravidagourmet.api.domain.enums.RoleProvider;

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
