package org.puravidagourmet.restaurante.controllers;

import static org.puravidagourmet.restaurante.exceptions.codes.PuraVidaErrorCodes.USU_REC004;

import org.puravidagourmet.restaurante.config.security.CurrentUser;
import org.puravidagourmet.restaurante.config.security.UserPrincipal;
import org.puravidagourmet.restaurante.db.repository.UsuarioRepository;
import org.puravidagourmet.restaurante.domain.entity.Usuario;
import org.puravidagourmet.restaurante.exceptions.PuraVidaExceptionHandler;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

  private final UsuarioRepository usuarioRepository;

  public UserController(UsuarioRepository usuarioRepository) {
    this.usuarioRepository = usuarioRepository;
  }

  @GetMapping("/me")
  @PreAuthorize("hasRole('USER')")
  public Usuario getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
    return usuarioRepository
        .findByEmail(userPrincipal.getName())
        .orElseThrow(() -> new PuraVidaExceptionHandler(USU_REC004, "id", userPrincipal.getName()));
  }
}
