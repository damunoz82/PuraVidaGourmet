package org.puravidagourmet.api.db.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import org.puravidagourmet.api.domain.entity.Producto;
import org.puravidagourmet.api.domain.entity.TipoProducto;
import org.puravidagourmet.api.domain.enums.FormatoCompra;
import org.puravidagourmet.api.domain.enums.UnidadMedidas;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class ProductoRepository extends BaseRepository<Producto> {

  private final String FIND_ALL =
      "select p.id, p.cantidad_por_unidad, p.coste_unitario, p.formato_compra, p.nombre, p.porcentaje_merma, "
          + "p.precio_de_compra, p.proveedor, p.unidad_medida , p.tipo_producto_id, tp.nombre as nombreTipoProducto, "
          + "tp.ubicacion  from producto p "
          + "join tipo_producto tp on p.tipo_producto_id = tp.id ";
  private final String FIND_BY_NOMBRE = FIND_ALL + " where p.nombre = ?";
  private final String FIND_BY_ID = FIND_ALL + " where p.id = ?";
  private final String SORT = "order by p.nombre";
  private final String CREATE_RECETA_CATEGORIA =
      "insert into producto (cantidad_por_unidad, coste_unitario, formato_compra, nombre, porcentaje_merma, "
          + "precio_de_compra, proveedor, unidad_medida, tipo_producto_id) "
          + "values (?, ?, ?, ?, ?, ?, ?, ?, ?)";

  private final String UPDATE_RECETA_CATEGORIA =
      "update producto set cantidad_por_unidad=?, coste_unitario=?, formato_compra=?, nombre=?, "
          + "porcentaje_merma=?, precio_de_compra=?, proveedor=?, unidad_medida=?, tipo_producto_id=? where id=?";

  private final String DELETE = "delete from producto where id = ?";

  private final RowMapper<Producto> rowMapper =
      (rs, rowNum) -> {
        TipoProducto tipoProducto =
            TipoProducto.builder()
                .id(rs.getLong("tipo_producto_id"))
                .nombre(rs.getString("nombreTipoProducto"))
                .ubicacion(rs.getString("ubicacion"))
                .build();
        return Producto.builder()
            .id(rs.getInt("id"))
            .cantidadPorUnidad(rs.getLong("cantidad_por_unidad"))
            .costeUnitario(rs.getFloat("coste_unitario"))
            .formatoCompra(FormatoCompra.getFormatoCompra(rs.getInt("formato_compra")))
            .nombre(rs.getString("nombre"))
            .porcentajeMerma(rs.getFloat("porcentaje_merma"))
            .precioDeCompra(rs.getLong("precio_de_compra"))
            .proveedor(rs.getString("proveedor"))
            .unidadMedida(UnidadMedidas.getUnidadMedida(rs.getInt("unidad_medida")))
            .tipoProducto(tipoProducto)
            .build();
      };

  public ProductoRepository(JdbcTemplate template) {
    super(template);
  }

  @Override
  protected void prepareStatement(PreparedStatement ps, Producto obj) throws SQLException {
    ps.setLong(1, obj.getCantidadPorUnidad());
    ps.setFloat(2, obj.getCosteUnitario());
    ps.setInt(3, obj.getFormatoCompra().ordinal());
    ps.setString(4, obj.getNombre());
    ps.setFloat(5, obj.getPorcentajeMerma());
    ps.setFloat(6, obj.getPrecioDeCompra());
    ps.setString(7, obj.getProveedor());
    ps.setInt(8, obj.getUnidadMedida().ordinal());
    ps.setLong(9, obj.getTipoProducto().getId());
  }

  public List<Producto> findAll() {
    return template.query(FIND_ALL + SORT, rowMapper);
  }

  public Optional<Producto> findByNombre(String nombre) {
    try {
      Producto producto = template.queryForObject(FIND_BY_NOMBRE, rowMapper, nombre);
      return Optional.ofNullable(producto);
    } catch (EmptyResultDataAccessException e) {
      return Optional.empty();
    }
  }

  public Optional<Producto> findById(long id) {
    try {
      Producto producto = template.queryForObject(FIND_BY_ID, rowMapper, id);
      return Optional.ofNullable(producto);
    } catch (EmptyResultDataAccessException e) {
      return Optional.empty();
    }
  }

  public Producto save(Producto producto) {
    if (producto.getId() <= 0) {
      long id = performInsert(CREATE_RECETA_CATEGORIA, producto);
      producto.setId(id);
      return producto;
    }
    template.update(
        UPDATE_RECETA_CATEGORIA,
        producto.getCantidadPorUnidad(),
        producto.getCosteUnitario(),
        producto.getFormatoCompra().ordinal(),
        producto.getNombre(),
        producto.getPorcentajeMerma(),
        producto.getPrecioDeCompra(),
        producto.getProveedor(),
        producto.getUnidadMedida().ordinal(),
        producto.getTipoProducto().getId(),
        producto.getId());
    return producto;
  }

  public void delete(long id) {
    template.update(DELETE, id);
  }
}
