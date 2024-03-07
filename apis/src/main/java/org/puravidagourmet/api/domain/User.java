package org.puravidagourmet.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import javax.validation.constraints.Email;
import lombok.*;
import org.puravidagourmet.api.domain.enums.AuthProvider;
import org.puravidagourmet.api.domain.enums.RoleProvider;

@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

  private long id;

  private String name;

  @Email private String email;

  @JsonIgnore private String password;

  private AuthProvider provider;

  private String providerId;

  //  @Convert(converter = StringListConverter.class)
  private List<RoleProvider> roles;

  private boolean enabled = false;
}
