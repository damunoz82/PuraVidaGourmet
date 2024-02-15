package org.puravidatgourmet.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private String name;

  @Email
  @Column(unique=true)
  private String email;

  @JsonIgnore private String password;

  private AuthProvider provider;

  private String providerId;

  @Convert(converter = StringListConverter.class)
  private List<RoleProvider> roles;

  private boolean enabled = false;
}
