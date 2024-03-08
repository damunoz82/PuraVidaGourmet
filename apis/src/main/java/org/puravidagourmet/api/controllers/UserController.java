package org.puravidagourmet.api.controllers;

import static org.puravidagourmet.api.exceptions.codes.PuraVidaErrorCodes.USU_REC002;
import static org.puravidagourmet.api.exceptions.codes.PuraVidaErrorCodes.USU_REC004;

import com.google.common.base.Strings;
import java.net.URI;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.puravidagourmet.api.config.security.CurrentUser;
import org.puravidagourmet.api.config.security.UserPrincipal;
import org.puravidagourmet.api.db.repository.UsuarioRepository;
import org.puravidagourmet.api.domain.entity.Usuario;
import org.puravidagourmet.api.exceptions.PuraVidaExceptionHandler;
import org.puravidagourmet.api.exceptions.codes.PuraVidaErrorCodes;
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
  private final UsuarioRepository usuarioRepository;

  private final PasswordEncoder passwordEncoder;

  public UserController(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
    this.usuarioRepository = usuarioRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @GetMapping("/me")
  @PreAuthorize("hasRole('USER')")
  public Usuario getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
    return usuarioRepository
        .findByEmail(userPrincipal.getName())
        .orElseThrow(() -> new PuraVidaExceptionHandler(USU_REC004, "id", userPrincipal.getName()));
  }

  @GetMapping
  @PreAuthorize("hasRole ('ADMIN')")
  public List<Usuario> getAll() {
    return usuarioRepository.findAll().stream()
        .sorted(Comparator.comparing((Usuario::getName)))
        .collect(Collectors.toList());
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasRole ('ADMIN')")
  public Usuario get(@PathVariable long id) {
    return usuarioRepository
        .findById(id)
        .orElseThrow(() -> new PuraVidaExceptionHandler(USU_REC004, "id", id));
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<String> update(@PathVariable long id, @RequestBody Usuario usuario) {
    usuario.setId(id);

    // get unchanged user from DB.
    Optional<Usuario> dbUser = usuarioRepository.findById(id);

    // check exists.
    if (dbUser.isEmpty()) {
      throw new PuraVidaExceptionHandler(USU_REC004, "id", id);
    }

    Usuario dbUsuario = dbUser.get();

    // check if email changed:
    if (!dbUsuario.getEmail().equals(usuario.getEmail())) {
      if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
        throw new PuraVidaExceptionHandler(PuraVidaErrorCodes.USU_REC001);
      }
    }

    // check if new password
    if (!Strings.isNullOrEmpty(usuario.getPassword())) {
      usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
    } else {
      usuario.setPassword(dbUser.get().getPassword());
    }

    usuarioRepository.save(usuario);

    URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
    return ResponseEntity.noContent().location(location).build();
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<String> disable(
      @PathVariable long id, @CurrentUser UserPrincipal userPrincipal) {

    // check user exists in the DB
    Usuario dbUsuario =
        usuarioRepository
            .findById(id)
            .orElseThrow(() -> new PuraVidaExceptionHandler(USU_REC004, "id", id));

    if (dbUsuario != null && dbUsuario.getEmail().equals(userPrincipal.getName())) {
      throw new PuraVidaExceptionHandler(USU_REC002);
    }
    assert dbUsuario != null;
    dbUsuario.setEnabled(false);

    usuarioRepository.save(dbUsuario);
    URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
    return ResponseEntity.noContent().location(location).build();
  }
}
