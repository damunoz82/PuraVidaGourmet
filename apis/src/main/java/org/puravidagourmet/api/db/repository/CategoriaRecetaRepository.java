package org.puravidagourmet.api.db.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import org.puravidagourmet.api.domain.entity.CategoriaReceta;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class CategoriaRecetaRepository extends BaseRepository<CategoriaReceta> {

  private final String FIND_ALL = "select id, nombre from categoria_receta";

  private final String FIND_BY_NOMBRE = FIND_ALL + " where nombre = ?";

  private final String FIND_BY_ID = FIND_ALL + " where id = ?";

  private final String CREATE_RECETA_CATEGORIA = "insert into categoria_receta (nombre) values (?)";

  private final String UPDATE_RECETA_CATEGORIA = "update categoria_receta set nombre=? where id=?";

  private final String DELETE = "delete from categoria_receta where id = ?";

  private static final RowMapper<CategoriaReceta> rowMapper = (rs, rowNum) -> CategoriaReceta.builder()
          .id(rs.getInt("id"))
          .nombre(rs.getString("nombre")).build();

  public CategoriaRecetaRepository(JdbcTemplate template) {
    super(template);
  }

  public List<CategoriaReceta> findAll() {
    return template.query(FIND_ALL, rowMapper);
  }

  public Optional<CategoriaReceta> findByNombre(String nombre) {
    try {
      CategoriaReceta categoriaReceta = template.queryForObject(FIND_BY_NOMBRE, rowMapper, nombre);
      return Optional.ofNullable(categoriaReceta);
    } catch(EmptyResultDataAccessException e) {
      return Optional.empty();
    }
  }

  public Optional<CategoriaReceta> findById(long id) {
    try {
      CategoriaReceta categoriaReceta = template.queryForObject(FIND_BY_ID, rowMapper, id);
      return Optional.ofNullable(categoriaReceta);
    } catch(EmptyResultDataAccessException e) {
      return Optional.empty();
    }
  }

  public CategoriaReceta save(CategoriaReceta categoriaReceta) {
    if (categoriaReceta.getId() <= 0) {
      long id = performInsert(CREATE_RECETA_CATEGORIA, categoriaReceta);
      categoriaReceta.setId(id);

      return categoriaReceta;
    }
    template.update(UPDATE_RECETA_CATEGORIA, categoriaReceta.getNombre(), categoriaReceta.getId());
    return categoriaReceta;
  }

  public void delete(long id) {
    template.update(DELETE, id);
  }

  @Override
  protected void prepareStatement(PreparedStatement ps, CategoriaReceta receta) throws SQLException {
    ps.setString(1, receta.getNombre());
  }
}
