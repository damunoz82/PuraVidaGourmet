package org.puravidagourmet.api.db.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import org.puravidagourmet.api.domain.entity.Mesa;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class MesaRepository extends BaseRepository<Mesa> {

  private static final RowMapper<Mesa> rowMapper =
      (rs, rowNum) ->
          Mesa.builder()
              .id(rs.getLong("id"))
              .nombre(rs.getString("nombre"))
              .capacidad(rs.getInt("capacidad"))
              .build();
  private final String FIND_ALL = "select id, nombre, capacidad from mesas_restaurante ";
  private final String SORT = "order by nombre";
  private final String FIND_BY_ID = FIND_ALL + "where id=?";
  private final String FIND_BY_NAME = FIND_ALL + "where nombre=?";
  private final String INSERT = "insert into mesas_restaurante (nombre, capacidad) values (?, ?)";
  private final String UPDATE = "update mesas_restaurante set nombre=?, capacidad=? where id=?";
  private final String DELETE = "delete from mesas_restaurante where id=?";
  ;

  public MesaRepository(JdbcTemplate template) {
    super(template);
  }

  public List<Mesa> findAll() {
    return template.query(FIND_ALL + SORT, rowMapper);
  }

  public Optional<Mesa> findById(long id) {
    try {
      Mesa mesa = template.queryForObject(FIND_BY_ID, rowMapper, id);
      return Optional.ofNullable(mesa);
    } catch (EmptyResultDataAccessException e) {
      return Optional.empty();
    }
  }

  public Optional<Mesa> findByName(String nombre) {
    try {
      Mesa mesa = template.queryForObject(FIND_BY_NAME, rowMapper, nombre);
      return Optional.ofNullable(mesa);
    } catch (EmptyResultDataAccessException e) {
      return Optional.empty();
    }
  }

  public Mesa save(Mesa mesa) {
    if (mesa.getId() <= 0) {
      long id = performInsert(INSERT, mesa);
      mesa.setId(id);

      return mesa;
    }
    template.update(UPDATE, mesa.getNombre(), mesa.getCapacidad(), mesa.getId());
    return mesa;
  }

  public void delete(long id) {
    template.update(DELETE, id);
  }

  @Override
  protected void prepareStatement(PreparedStatement ps, Mesa obj) throws SQLException {
    ps.setString(1, obj.getNombre());
    ps.setInt(2, obj.getCapacidad());
  }
}
