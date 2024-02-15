package org.puravidatgourmet.api.controllers;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import org.puravidatgourmet.api.config.security.CurrentUser;
import org.puravidatgourmet.api.config.security.UserPrincipal;
import org.puravidatgourmet.api.db.repository.UsuarioRepository;
import org.puravidatgourmet.api.domain.User;
import org.puravidatgourmet.api.exceptions.BadRequestException;
import org.puravidatgourmet.api.exceptions.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/user")
public class UserController {

  private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
  @Autowired private UsuarioRepository userRepository;

  @GetMapping("/me")
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

  @GetMapping
  @PreAuthorize("hasRole ('ADMIN')")
  public List<User> getAll() {
    try {
      LOGGER.info("START: getAll");
      return userRepository.findAll();

    } finally {
      LOGGER.info("END: getAll");
    }
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasRole ('ADMIN')")
  public User get(@PathVariable long id) {
    try {
      LOGGER.info("START: get with id: {}", id);
      return userRepository
          .findById(id)
          .orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", id));

    } finally {
      LOGGER.info("END: get with id: {}", id);
    }
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<String> update(
      @PathVariable long id, @RequestBody User user) {
    try {
      LOGGER.info("START: Update with id: {}, and user: {}", id, user);

      // check user exists in the DB
      Optional<User> dbUser = userRepository.findByEmail(user.getEmail());
      if (dbUser.isPresent() && dbUser.get().getId() != id) {
        throw new BadRequestException("Ya existe un usuario con ese correo electronico.");
      }

      userRepository.save(user);
      URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
      return ResponseEntity.noContent().location(location).build();
    } finally {
      LOGGER.info("END: Update with id: {}, and user: {}", id, user);
    }
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<String> disable(
      @PathVariable long id, @CurrentUser UserPrincipal userPrincipal) {
    try {
      LOGGER.info("START: Disable with id: {}", id);

      // check user exists in the DB
      User dbUser = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", id));
      if (dbUser != null && dbUser.getEmail().equals(userPrincipal.getName())) {
        throw new BadRequestException("Tu no puedes desactivar tu propia cuenta.");
      }
      assert dbUser != null;
      dbUser.setEnabled(false);

      userRepository.save(dbUser);
      URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
      return ResponseEntity.noContent().location(location).build();
    } finally {
      LOGGER.info("END: Disable with id: {}", id);
    }
  }
}
