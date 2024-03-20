package org.puravidagourmet.api.db.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import org.puravidagourmet.api.domain.entity.TipoProducto;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class TipoProductoRepository extends BaseRepository<TipoProducto> {

  private final String FIND_ALL = "select id, nombre, ubicacion from tipo_producto ";

  private final String FIND_BY_NOMBRE = FIND_ALL + "where nombre = ?";

  private final String FIND_BY_ID = FIND_ALL + "where id = ?";

  private final String SORT = "order by nombre";

  private final String CREATE_RECETA_CATEGORIA =
      "insert into tipo_producto (nombre, ubicacion) values" + "(?, ?)";

  private final String UPDATE_RECETA_CATEGORIA =
      "update tipo_producto set nombre=?, ubicacion=? where id=?";

  private final String DELETE = "delete from tipo_producto where id = ?";

  private final RowMapper<TipoProducto> rowMapper =
      (rs, rowNum) ->
          TipoProducto.builder()
              .id(rs.getInt("id"))
              .ubicacion(rs.getString("ubicacion"))
              .nombre(rs.getString("nombre"))
              .build();

  public TipoProductoRepository(JdbcTemplate template) {
    super(template);
  }

  @Override
  protected void prepareStatement(PreparedStatement ps, TipoProducto obj) throws SQLException {
    ps.setString(1, obj.getNombre());
    ps.setString(2, obj.getUbicacion());
  }

  public List<TipoProducto> findAll() {
    return template.query(FIND_ALL + SORT, rowMapper);
  }

  public Optional<TipoProducto> findByNombre(String nombre) {
    try {
      TipoProducto tipoProducto = template.queryForObject(FIND_BY_NOMBRE, rowMapper, nombre);
      return Optional.ofNullable(tipoProducto);
    } catch (EmptyResultDataAccessException e) {
      return Optional.empty();
    }
  }

  public Optional<TipoProducto> findById(long id) {
    try {
      TipoProducto tipoProducto = template.queryForObject(FIND_BY_ID, rowMapper, id);
      return Optional.ofNullable(tipoProducto);
    } catch (EmptyResultDataAccessException e) {
      return Optional.empty();
    }
  }

  public TipoProducto save(TipoProducto tipoProducto) {
    if (tipoProducto.getId() <= 0) {
      long id = performInsert(CREATE_RECETA_CATEGORIA, tipoProducto);
      tipoProducto.setId(id);
      return tipoProducto;
    }
    template.update(
        UPDATE_RECETA_CATEGORIA,
        tipoProducto.getNombre(),
        tipoProducto.getUbicacion(),
        tipoProducto.getId());
    return tipoProducto;
  }

  public void delete(long id) {
    template.update(DELETE, id);
  }
}
