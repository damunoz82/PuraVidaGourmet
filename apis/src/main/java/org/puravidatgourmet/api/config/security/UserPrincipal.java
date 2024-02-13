package org.puravidatgourmet.api.config.security;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;
import org.puravidatgourmet.api.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class UserPrincipal implements OAuth2User, UserDetails {
  /** */
  private static final long serialVersionUID = -8309183157087871563L;

  private String email;
  private String password;
  private Collection<? extends GrantedAuthority> authorities;
  private Map<String, Object> attributes;

  public UserPrincipal(
      String email, String password, Collection<? extends GrantedAuthority> authorities) {
    this.email = email;
    this.password = password;
    this.authorities = authorities;
  }

  public static UserPrincipal create(User user) {
    return new UserPrincipal(
        user.getEmail(),
        user.getPassword(),
        user.getRoles().stream().map(m -> new SimpleGrantedAuthority(m.name())).collect(Collectors.toList()));
  }

  public static UserPrincipal create(User user, Map<String, Object> attributes) {
    UserPrincipal userPrincipal = UserPrincipal.create(user);
    userPrincipal.setAttributes(attributes);
    return userPrincipal;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public Map<String, Object> getAttributes() {
    return attributes;
  }

  public void setAttributes(Map<String, Object> attributes) {
    this.attributes = attributes;
  }

  @Override
  public String getName() {
    return email;
  }

  @Override
  public String toString() {
    return "UserPrincipal{"
        + "email='"
        + email
        + '\''
        + ", authorities="
        + authorities
        + ", attributes="
        + attributes
        + '}';
  }
}
