package org.puravidatgourmet.api.services;

import org.puravidatgourmet.api.config.security.UserPrincipal;
import org.puravidatgourmet.api.db.repository.UsuarioRepository;
import org.puravidatgourmet.api.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Created by rajeevkumarsingh on 02/08/17. */
@Service
public class CustomUserDetailsService implements UserDetailsService {

  @Autowired
  UsuarioRepository usuarioRepository;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    User user =
        usuarioRepository
            .findByEmail(email)
            .orElseThrow(
                () -> new UsernameNotFoundException("User not found with email : " + email));

    return UserPrincipal.create(user);
  }
}
