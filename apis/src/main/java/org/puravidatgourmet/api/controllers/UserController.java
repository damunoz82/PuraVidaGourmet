package org.puravidatgourmet.api.controllers;

import org.puravidatgourmet.api.config.security.CurrentUser;
import org.puravidatgourmet.api.config.security.UserPrincipal;
import org.puravidatgourmet.api.db.repository.UsuarioRepository;
import org.puravidatgourmet.api.domain.User;
import org.puravidatgourmet.api.exceptions.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

  private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
  @Autowired private UsuarioRepository userRepository;

  @GetMapping("/user/me")
  @PreAuthorize("hasRole('USER')")
  public User getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
    try {
      LOGGER.info("END: getCurrentUser with userPrincipal: {}", userPrincipal);
      return userRepository
          .findByEmail(userPrincipal.getName())
          .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getName()));
    } finally {
      LOGGER.info("END: getCurrentUser with userPrincipal: {}", userPrincipal);
    }
  }
}
