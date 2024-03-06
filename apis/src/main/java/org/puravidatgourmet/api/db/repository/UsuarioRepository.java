package org.puravidatgourmet.api.db.repository;

import org.puravidatgourmet.api.domain.User;
import org.puravidatgourmet.api.domain.enums.AuthProvider;
import org.puravidatgourmet.api.domain.enums.RoleProvider;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class UsuarioRepository {

    private final String FIND_ALL = "select id, email, enabled, name, password, provider, provider_id, roles from usuario";

    private final String FIND_BY_EMAIL = FIND_ALL + " where email = ?";

    private final String FIND_BY_ID = FIND_ALL + " where id = ?";

    private final String CREATE_USER = "insert into usuario (email, enabled, name, password, provider, provider_id, roles) values" +
            "(?, ?, ?, ?, ?, ?, ?)";

    private final String UPDATE_USER = "update usuario set email=?, enabled=?, name=?, password=?, roles=? where id=?";

//    private final String DISABLE_USER = "update usuario set enabled = false where id = ?";

    private final JdbcTemplate template;

    public static final RowMapper<User> rowMapper = (rs, rowNum) -> User.builder()
            .id(rs.getInt("id"))
            .email(rs.getString("email"))
            .enabled(rs.getBoolean("enabled"))
            .name(rs.getString("name"))
            .password(rs.getString("password"))
            .provider(AuthProvider.LOCAL)
            .roles(Arrays.stream(rs.getString("roles").split(",")).map(RoleProvider::valueOf).collect(Collectors.toList()))
            .build();

    public UsuarioRepository(JdbcTemplate template) {
        this.template = template;
    }

    public List<User> findAll() {
        return template.query(FIND_ALL, rowMapper);
    }

    public Optional<User> findByEmail(String email) {
        User user = template.queryForObject(FIND_BY_EMAIL, rowMapper, email);
        return Optional.ofNullable(user);
    }

    public Optional<User> findById(long id) {
        User user = template.queryForObject(FIND_BY_ID, rowMapper, id);
        return Optional.ofNullable(user);
    }

    public User save(User usuario) {
        if (usuario.getId() <= 0) {
            long id = template.update(CREATE_USER, usuario.getEmail(), usuario.isEnabled(), usuario.getName(),
                    usuario.getPassword(), usuario.getProvider(), usuario.getProviderId(),
                    usuario.getRoles().stream().map(Enum::name).collect(Collectors.joining(",")));
            usuario.setId(id);
            return usuario;
        } else {
            template.update(UPDATE_USER,
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
