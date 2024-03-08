package org.puravidagourmet.api.utils;

import java.util.List;
import java.util.Optional;
import org.puravidagourmet.api.controllers.UserController;
import org.puravidagourmet.api.db.repository.UsuarioRepository;
import org.puravidagourmet.api.domain.entity.Usuario;
import org.puravidagourmet.api.domain.enums.AuthProvider;
import org.puravidagourmet.api.domain.enums.RoleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataPopulationTool implements CommandLineRunner {

  private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
  @Autowired private UsuarioRepository usuarioRepository;
  @Autowired private PasswordEncoder passwordEncoder;

  @Override
  public void run(String... args) throws Exception {
    try {
      LOGGER.info("START - checking if needed db data is present.");

      Optional<Usuario> adminUser = usuarioRepository.findByEmail("dmunoz.hon@gmail.com");
      if (adminUser.isEmpty()) {
        Usuario usuario = new Usuario();
        usuario.setName("Admin");
        usuario.setEmail("dmunoz.hon@gmail.com");
        usuario.setPassword(passwordEncoder.encode("password"));
        usuario.setProvider(AuthProvider.LOCAL);
        usuario.setEnabled(true);
        usuario.setRoles(List.of(RoleProvider.ROLE_USER, RoleProvider.ROLE_ADMIN));

        usuarioRepository.save(usuario);
      }
    } finally {
      LOGGER.info("END - checking if needed db data is present.");
    }
  }
}
