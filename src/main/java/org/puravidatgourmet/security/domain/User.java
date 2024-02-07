package org.puravidatgourmet.security.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.puravidatgourmet.security.domain.converters.StringListConverter;
import org.puravidatgourmet.security.utils.AuthProvider;
import org.puravidatgourmet.security.utils.RoleProvider;

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
