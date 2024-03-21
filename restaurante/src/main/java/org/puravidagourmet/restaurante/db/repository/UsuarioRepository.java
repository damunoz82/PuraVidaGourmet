package org.puravidagourmet.restaurante.db.repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.puravidagourmet.restaurante.domain.entity.Usuario;
import org.puravidagourmet.restaurante.domain.enums.AuthProvider;
import org.puravidagourmet.restaurante.domain.enums.RoleProvider;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class UsuarioRepository {

  private static final RowMapper<Usuario> rowMapper =
      (rs, rowNum) ->
          Usuario.builder()
              .id(rs.getInt("id"))
              .email(rs.getString("email"))
              .enabled(rs.getBoolean("enabled"))
              .name(rs.getString("name"))
              .password(rs.getString("password"))
              .provider(AuthProvider.LOCAL)
              .roles(
                  Arrays.stream(rs.getString("roles").split(","))
                      .map(RoleProvider::valueOf)
                      .collect(Collectors.toList()))
              .build();
  private final String FIND_ALL =
      "select id, email, enabled, name, password, provider, provider_id, roles from usuario ";
  private final String FIND_BY_EMAIL = FIND_ALL + "where email = ?";
  private final String FIND_BY_ID = FIND_ALL + "where id = ?";
  private final String SORT = "order by name";
  private final String CREATE_USER =
      "insert into usuario (email, enabled, name, password, provider, provider_id, roles) values"
          + "(?, ?, ?, ?, ?, ?, ?)";
  private final String UPDATE_USER =
      "update usuario set email=?, enabled=?, name=?, password=?, roles=? where id=?";

  private final JdbcTemplate template;

  public UsuarioRepository(JdbcTemplate template) {
    this.template = template;
  }

  public List<Usuario> findAll() {
    return template.query(FIND_ALL + SORT, rowMapper);
  }

  public Optional<Usuario> findByEmail(String email) {
    try {
      Usuario usuario = template.queryForObject(FIND_BY_EMAIL, rowMapper, email);
      return Optional.ofNullable(usuario);
    } catch (EmptyResultDataAccessException e) {
      return Optional.empty();
    }
  }
}
