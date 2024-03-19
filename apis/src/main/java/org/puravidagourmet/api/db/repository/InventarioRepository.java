package org.puravidagourmet.api.db.repository;

import java.sql.*;
import java.util.List;
import java.util.Optional;
import org.puravidagourmet.api.domain.entity.Departamento;
import org.puravidagourmet.api.domain.entity.Inventario;
import org.puravidagourmet.api.domain.entity.InventarioDetalle;
import org.puravidagourmet.api.domain.entity.Usuario;
import org.puravidagourmet.api.domain.enums.EstadoInventario;
import org.puravidagourmet.api.domain.enums.FormatoCompra;
import org.puravidagourmet.api.domain.enums.UnidadMedidas;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class InventarioRepository extends BaseRepository<Inventario> {

  private static final RowMapper<Inventario> inventarioRowMapper =
      (rs, rowNum) -> {
        Usuario responsable =
            Usuario.builder()
                .id(rs.getLong("responsable_id"))
                .email(rs.getString("email"))
                .name(rs.getString("name"))
                .build();
        Usuario modifica =
            Usuario.builder()
                .id(rs.getLong("usuario_modifica_id"))
                .email(rs.getString("uemail"))
                .name(rs.getString("uname"))
                .build();
        Departamento departamento =
            Departamento.builder()
                .id(rs.getLong("departamento_id"))
                .nombre(rs.getString("nombre"))
                .build();
        return Inventario.builder()
            .id(rs.getLong("id"))
            .fechaCreacion(rs.getTimestamp("fecha_creacion"))
            .fechaModificacion(rs.getTimestamp("fecha_modificacion"))
            .comentario(rs.getString("comentario"))
            .departamento(departamento)
            .responsable(responsable)
            .usuarioModifica(modifica)
            .periodoMeta(rs.getString("periodo_meta"))
            .estado(EstadoInventario.getEstadoInventario(rs.getInt("estado")))
            .totalValorEnBodega((rs.getFloat("total_valor_bodega")))
            .build();
      };

  private static final RowMapper<InventarioDetalle> detalleRowMapper =
      (rs, rowNum) ->
          InventarioDetalle.builder()
              .detalleId(rs.getLong("detalle_id"))
              .nombreProducto(rs.getString("nombre_producto"))
              .categoriaProducto(rs.getString("categoria_producto"))
              .ubicacionProducto(rs.getString("ubicacion_producto"))
              .formatoCompraProducto(
                  FormatoCompra.getFormatoCompra(rs.getInt("formato_compra_producto")))
              .unidadMedidaProducto(
                  UnidadMedidas.getUnidadMedida(rs.getInt("unidad_medida_producto")))
              .cantidadUnidadProducto(rs.getInt("cantidad_unidad_producto"))
              .precioCompraProducto(rs.getInt("precio_compra_producto"))
              .cantidadEnBodega(rs.getFloat("cantidad_bodega"))
              .valor(rs.getLong("valor_en_bodega"))
              .build();

  private final String FIND_ALL =
      "select i.id, i.fecha_creacion, i.fecha_modificacion, i.comentario, i.departamento_id, d.nombre, "
          + "i.periodo_meta, i.estado, i.responsable_id, u.email, u.name, i.usuario_modifica_id, um.email as uemail, "
          + "um.name as uname, i.total_valor_bodega  from inventario i "
          + "join departamento d on i.departamento_id = d.id "
          + "join usuario u on i.responsable_id = u.id "
          + "left join usuario um on i.usuario_modifica_id = um.id ";
  private final String ORDER_BY = " order by i.fecha_creacion DESC ";
  private final String FIND_BY_ID = FIND_ALL + " WHERE i.id = ?" + ORDER_BY;
  private final String FIND_DETALLE_INVENTARIO =
      "select id.detalle_id, id.nombre_producto, id.categoria_producto, id.ubicacion_producto, id.formato_compra_producto, "
          + "id.unidad_medida_producto, id.cantidad_unidad_producto, id.precio_compra_producto, id.cantidad_bodega, "
          + "id.valor_en_bodega from inventario_detalle id "
          + "where id.inventario_id = ? order by id.categoria_producto, id.nombre_producto";

  private final String UPDATE_INVENTARIO_DETALLE =
      "update inventario_detalle set cantidad_bodega=?, valor_en_bodega =?  where detalle_id =?";

  private final String UPDATE_INVENTARIO =
      "update inventario set fecha_modificacion=CURRENT_TIMESTAMP, comentario=?, "
          + "periodo_meta=?, usuario_modifica_id=?, total_valor_bodega=? where id=?";

  private final String CAMBIAR_ESTADO =
      "update inventario set estado=?, usuario_modifica_id=?, fecha_modificacion=CURRENT_TIMESTAMP where id=?";

  public InventarioRepository(JdbcTemplate template) {
    super(template);
  }

  public List<Inventario> findAll() {
    return template.query(FIND_ALL + ORDER_BY, inventarioRowMapper);
  }

  public Optional<Inventario> findById(long id) {
    try {
      Inventario inventario = template.queryForObject(FIND_BY_ID, inventarioRowMapper, id);
      return Optional.of(inventario);
    } catch (EmptyResultDataAccessException e) {
      return Optional.empty();
    }
  }

  public List<InventarioDetalle> findDetalleByid(long id) {
    return template.query(FIND_DETALLE_INVENTARIO, detalleRowMapper, id);
  }

  public Inventario iniciarInventario(Inventario inventario) {
    long inventarioId = 0;

    try (Connection conn = template.getDataSource().getConnection();
        CallableStatement callable = conn.prepareCall("call iniciarInventario(?, ?, ?, ?, ?, ?)")) {
      callable.registerOutParameter(1, Types.BIGINT);
      callable.setLong(1, inventario.getDepartamento().getId());
      callable.setLong(2, inventario.getResponsable().getId());
      callable.setString(3, inventario.getPeriodoMeta());
      callable.setString(4, inventario.getComentario());
      callable.setInt(5, inventario.getEstado().ordinal());
      callable.setLong(6, inventarioId);
      callable.execute();
      inventario.setId(callable.getLong(1));

    } catch (Exception e) {
      e.printStackTrace();
    }

    return inventario;
  }

  public void actualizarInventario(Inventario inventario) {
    // actualizar detalles
    template.update(
        UPDATE_INVENTARIO,
        inventario.getComentario(),
        inventario.getPeriodoMeta(),
        inventario.getUsuarioModifica().getId(),
        inventario.getTotalValorEnBodega(),
        inventario.getId());

    // actualizar info de valor en inventario.
    template.batchUpdate(
        UPDATE_INVENTARIO_DETALLE,
        new BatchPreparedStatementSetter() {
          @Override
          public void setValues(PreparedStatement ps, int i) throws SQLException {
            ps.setFloat(1, inventario.getDetalle().get(i).getCantidadEnBodega());
            ps.setFloat(2, inventario.getDetalle().get(i).getValor());
            ps.setLong(3, inventario.getDetalle().get(i).getDetalleId());
          }

          @Override
          public int getBatchSize() {
            return inventario.getDetalle().size();
          }
        });
  }

  public boolean existsById(long id) {
    try {
      Inventario inventario = template.queryForObject(FIND_BY_ID, inventarioRowMapper, id);
      return true;
    } catch (EmptyResultDataAccessException e) {
      return false;
    }
  }

  public void cancel(Inventario inventario) {
    template.update(
        CAMBIAR_ESTADO,
        inventario.getEstado().ordinal(),
        inventario.getUsuarioModifica().getId(),
        inventario.getId());
  }

  @Override
  protected void prepareStatement(PreparedStatement ps, Inventario obj) throws SQLException {}
}
