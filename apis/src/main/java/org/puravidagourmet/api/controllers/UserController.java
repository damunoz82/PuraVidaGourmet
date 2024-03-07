package org.puravidagourmet.api.controllers;

import com.google.common.base.Strings;
import java.net.URI;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.puravidagourmet.api.config.security.CurrentUser;
import org.puravidagourmet.api.config.security.UserPrincipal;
import org.puravidagourmet.api.db.repository.UsuarioRepository;
import org.puravidagourmet.api.domain.User;
import org.puravidagourmet.api.exceptions.BadRequestException;
import org.puravidagourmet.api.exceptions.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
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
  @Autowired private UsuarioRepository usuarioRepository;

  @Autowired private PasswordEncoder passwordEncoder;

  @GetMapping("/me")
  @PreAuthorize("hasRole('USER')")
  public User getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
    try {
      LOGGER.info("END: getCurrentUser with userPrincipal: {}", userPrincipal);
      return usuarioRepository
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
      return usuarioRepository.findAll().stream()
              .sorted(Comparator.comparing((User::getName)))
              .collect(Collectors.toList());

    } finally {
      LOGGER.info("END: getAll");
    }
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasRole ('ADMIN')")
  public User get(@PathVariable long id) {
    try {
      LOGGER.info("START: get with id: {}", id);
      return usuarioRepository
          .findById(id)
          .orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", id));

    } finally {
      LOGGER.info("END: get with id: {}", id);
    }
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<String> update(@PathVariable long id, @RequestBody User user) {
    try {
      LOGGER.info("START: Update with id: {}, and user: {}", id, user);
      user.setId(id);

      // get unchanged user from DB.
      Optional<User> dbUser = usuarioRepository.findById(id);

      // check exists.
      if (dbUser.isEmpty()) {
        throw new ResourceNotFoundException("Usuario", "id", id);
      }

      User dbUsuario = dbUser.get();

      // check if email changed:
      if (!dbUsuario.getEmail().equals(user.getEmail())) {
        if (usuarioRepository.findByEmail(user.getEmail()).isPresent()) {
          throw new BadRequestException("Ya existe un usuario con ese correo electronico.");
        }
      }

      // check if new password
      if (!Strings.isNullOrEmpty(user.getPassword())) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
      } else {
        user.setPassword(dbUser.get().getPassword());
      }

      usuarioRepository.save(user);

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
      User dbUser =
          usuarioRepository
              .findById(id)
              .orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", id));

      if (dbUser != null && dbUser.getEmail().equals(userPrincipal.getName())) {
        throw new BadRequestException("Tu no puedes desactivar tu propia cuenta.");
      }
      assert dbUser != null;
      dbUser.setEnabled(false);

      usuarioRepository.save(dbUser);
      URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
      return ResponseEntity.noContent().location(location).build();
    } finally {
      LOGGER.info("END: Disable with id: {}", id);
    }
  }
}
