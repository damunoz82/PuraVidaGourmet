package org.puravidagourmet.api.db.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import org.puravidagourmet.api.domain.entity.Departamento;
import org.puravidagourmet.api.domain.entity.Usuario;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class DepartamentoRepository extends BaseRepository<Departamento> {

  private final String FIND_ALL =
      "select d.id, d.nombre, d.responsable_id, u.name, u.email  from departamento d join usuario u on d.responsable_id = u.id";

  private final String FIND_BY_ID = FIND_ALL + " where d.id = ?";

  private final String FIND_BY_NOMBRE = FIND_ALL + " where nombre = ?";

  private final String CREATE_DEPARTAMENTO =
      "insert into departamento (nombre, responsable_id) values" + "(?, ?)";

  private final String UPDATE_DEPARTAMENTO =
      "update departamento set nombre=?, responsable)id=? where id=?";

  private final String DELETE = "delete from departamento where id = ?";

  private final RowMapper<Departamento> rowMapper =
      (rs, rowNum) -> {
        Usuario responsable =
            Usuario.builder()
                .id(rs.getInt("responsable_id"))
                .name(rs.getString("name"))
                .email(rs.getString("email"))
                .build();
        return Departamento.builder()
            .id(rs.getInt("id"))
            .nombre(rs.getString("nombre"))
            .responsable(responsable)
            .build();
      };

  public DepartamentoRepository(JdbcTemplate template) {
    super(template);
  }

  @Override
  protected void prepareStatement(PreparedStatement ps, Departamento obj) throws SQLException {
    ps.setString(1, obj.getNombre());
    ps.setLong(2, obj.getResponsable().getId());
  }

  public List<Departamento> findAll() {
    return template.query(FIND_ALL, rowMapper);
  }

  public Optional<Departamento> findById(long id) {
    try {
      Departamento departamento = template.queryForObject(FIND_BY_ID, rowMapper, id);
      return Optional.ofNullable(departamento);
    } catch (EmptyResultDataAccessException e) {
      return Optional.empty();
    }
  }

  public Optional<Departamento> findByNombre(String nombre) {
    try {
      Departamento departamento = template.queryForObject(FIND_BY_NOMBRE, rowMapper, nombre);
      return Optional.ofNullable(departamento);
    } catch (EmptyResultDataAccessException e) {
      return Optional.empty();
    }
  }

  public Departamento save(Departamento departamento) {
    if (departamento.getId() <= 0) {
      long id = performInsert(CREATE_DEPARTAMENTO, departamento);
      departamento.setId(id);
      return departamento;
    }
    template.update(
        UPDATE_DEPARTAMENTO,
        departamento.getNombre(),
        departamento.getResponsable().getId(),
        departamento.getId());
    return departamento;
  }

  public void delete(long id) {
    template.update(DELETE, id);
  }
}
