package org.puravidatgourmet.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.List;
import javax.validation.constraints.Email;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.puravidatgourmet.api.domain.converters.StringListConverter;
import org.puravidatgourmet.api.utils.AuthProvider;
import org.puravidatgourmet.api.utils.RoleProvider;

@Data
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "usuario")
public class User {

  private String name;

  @Email @Id private String email;

  private String imageUrl;

  private Boolean emailVerified = false;

  @JsonIgnore private String password;

  private AuthProvider provider;

  //	private String roles;

  private String providerId;

  @Convert(converter = StringListConverter.class)
  private List<RoleProvider> roles;
}
