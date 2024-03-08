package org.puravidagourmet.api.services;

import org.puravidagourmet.api.config.security.UserPrincipal;
import org.puravidagourmet.api.db.repository.UsuarioRepository;
import org.puravidagourmet.api.domain.entity.Usuario;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Created by rajeevkumarsingh on 02/08/17. */
@Service
public class CustomUserDetailsService implements UserDetailsService {

  private final UsuarioRepository usuarioRepository;

  public CustomUserDetailsService(UsuarioRepository usuarioRepository) {
    this.usuarioRepository = usuarioRepository;
  }

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Usuario usuario =
        usuarioRepository
            .findByEmail(email)
            .orElseThrow(
                () -> new UsernameNotFoundException("User not found with email : " + email));

    return UserPrincipal.create(usuario);
  }
}
