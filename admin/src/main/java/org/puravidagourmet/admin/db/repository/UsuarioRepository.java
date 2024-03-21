package org.puravidagourmet.admin.db.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.puravidagourmet.admin.domain.entity.Usuario;
import org.puravidagourmet.admin.domain.enums.AuthProvider;
import org.puravidagourmet.admin.domain.enums.RoleProvider;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class UsuarioRepository extends BaseRepository<Usuario> {

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

  public UsuarioRepository(JdbcTemplate template) {
    super(template);
  }

  @Override
  protected void prepareStatement(PreparedStatement ps, Usuario obj) throws SQLException {
    ps.setString(1, obj.getEmail());
    ps.setBoolean(2, obj.isEnabled());
    ps.setString(3, obj.getName());
    ps.setString(4, obj.getPassword());
    ps.setLong(5, obj.getProvider().ordinal());
    ps.setString(6, obj.getProviderId());
    ps.setString(7, obj.getRoles().stream().map(Enum::name).collect(Collectors.joining(",")));
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

  public Optional<Usuario> findById(long id) {
    try {
      Usuario usuario = template.queryForObject(FIND_BY_ID, rowMapper, id);
      return Optional.ofNullable(usuario);
    } catch (EmptyResultDataAccessException e) {
      return Optional.empty();
    }
  }

  public Usuario save(Usuario usuario) {
    if (usuario.getId() <= 0) {
      long id = performInsert(CREATE_USER, usuario);
      usuario.setId(id);
      return usuario;
    } else {
      template.update(
          UPDATE_USER,
          usuario.getEmail(),
          usuario.isEnabled(),
          usuario.getName(),
          usuario.getPassword(),
          usuario.getRoles().stream().map(Enum::name).collect(Collectors.joining(",")),
          usuario.getId());
      return usuario;
    }
  }
}
