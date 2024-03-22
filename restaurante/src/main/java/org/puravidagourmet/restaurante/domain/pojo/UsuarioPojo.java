package org.puravidagourmet.restaurante.domain.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class UsuarioPojo {

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private String name;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private String email;
}
