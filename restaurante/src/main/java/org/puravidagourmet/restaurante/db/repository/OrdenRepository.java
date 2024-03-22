package org.puravidagourmet.restaurante.db.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import org.puravidagourmet.restaurante.domain.entity.DetalleOrden;
import org.puravidagourmet.restaurante.domain.entity.ItemMenu;
import org.puravidagourmet.restaurante.domain.entity.Orden;
import org.puravidagourmet.restaurante.domain.entity.Usuario;
import org.puravidagourmet.restaurante.domain.enums.EstadoDetalleOrden;
import org.puravidagourmet.restaurante.domain.enums.OrdenEstado;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class OrdenRepository extends BaseRepository<Orden> {

  private final String FIND_ALL =
      "select o.id, mesa_id, mr.nombre as nombre_mesa, mesero_id, fecha_creacion, estado, total_neto, impuestos, total, fecha_finalizacion from ordenes o "
          + "join mesas_restaurante mr on (o.mesa_id = mr.id) ";
  private final String FIND_BY_ID =
      "select o.id, mesa_id, mr.nombre as nombre_mesa, mesero_id, fecha_creacion, o.estado, total_neto, impuestos, total, fecha_finalizacion, "
          + "do2.detalle_id, im.item_menu_id, im.nombre_comercial, im.precio_venta, d.nombre as destino, do2.cantidad, do2.estado as detalle_estado from ordenes o "
          + "join detalle_orden do2 on (do2.orden_id = o.id) "
          + "join item_menu im on (do2.item_id = im.item_menu_id) "
          + "join departamento d on (im.dep_responsable = d.id) "
          + "join mesas_restaurante mr on (o.mesa_id = mr.id) "
          + "where o.id=?";
  private final String SORT = "order by fecha_creacion desc";
  private final String INSERT =
      "insert into ordenes (mesa_id, mesero_id, fecha_creacion, estado) values (?, ?, CURRENT_TIMESTAMP, ?)";
  private final String UPDATE = "update ordenes set mesa_id=? where id=?";
  private final String INSERT_DETALLE =
      "insert into detalle_orden (orden_id, item_id, cantidad, estado) values (?, ?, ?, ?)";
  private final String TERMINAR =
      "update ordenes set estado=?, total_neto=?, impuestos=?, total=?, fecha_finalizacion=CURRENT_TIMESTAMP where id=?";
  private final RowMapper<Orden> rowMapper =
      (rs, rowNum) ->
          Orden.builder()
              .id(rs.getLong("id"))
              .mesaId(rs.getLong("mesa_id"))
              .nombreMesa(rs.getString("nombre_mesa"))
              .mesero(Usuario.builder().email(rs.getString("mesero_id")).build())
              .fechaCreacion(rs.getTimestamp("fecha_creacion"))
              .estado(OrdenEstado.getOrdenEstado(rs.getInt("estado")))
              .totalNeto(rs.getFloat("total_neto"))
              .impuestos(rs.getFloat("impuestos"))
              .total(rs.getFloat("total"))
              .fechaFinalizacion(rs.getTimestamp("fecha_finalizacion"))
              .build();
  private Map<Long, Orden> ordenCompletaBuilder = new HashMap<>();
  private final RowMapper<Orden> ordenCompletaRowMapper =
      (rs, rowNum) -> {
        if (ordenCompletaBuilder.isEmpty()) {
          Orden orden =
              Orden.builder()
                  .id(rs.getLong("id"))
                  .mesaId(rs.getLong("mesa_id"))
                  .nombreMesa(rs.getString("nombre_mesa"))
                  .mesero(Usuario.builder().email(rs.getString("mesero_id")).build())
                  .fechaCreacion(rs.getTimestamp("fecha_creacion"))
                  .estado(OrdenEstado.getOrdenEstado(rs.getInt("estado")))
                  .totalNeto(rs.getFloat("total_neto"))
                  .impuestos(rs.getFloat("impuestos"))
                  .total(rs.getFloat("total"))
                  .fechaFinalizacion(rs.getTimestamp("fecha_finalizacion"))
                  .detalle(new ArrayList<>())
                  .build();
          DetalleOrden detalleOrden =
              DetalleOrden.builder()
                  .detalleId(rs.getLong("detalle_id"))
                  .item(
                      ItemMenu.builder()
                          .itemMenuId(rs.getLong("item_menu_id"))
                          .nombreComercial(rs.getString("nombre_comercial"))
                          .precioVenta(rs.getFloat("precio_venta"))
                          .destino(rs.getString("destino"))
                          .build())
                  .cantidad(rs.getInt("cantidad"))
                  .estadoDetalleOrden(
                      EstadoDetalleOrden.getEstadoDetalleOrden(rs.getInt("detalle_estado")))
                  .build();
          orden.getDetalle().add(detalleOrden);
          ordenCompletaBuilder.put(orden.getId(), orden);
        } else {
          Orden orden = ordenCompletaBuilder.get(rs.getLong("id"));
          orden
              .getDetalle()
              .add(
                  DetalleOrden.builder()
                      .detalleId(rs.getLong("detalle_id"))
                      .item(
                          ItemMenu.builder()
                              .itemMenuId(rs.getLong("item_menu_id"))
                              .nombreComercial(rs.getString("nombre_comercial"))
                              .precioVenta(rs.getFloat("precio_venta"))
                              .destino(rs.getString("destino"))
                              .build())
                      .cantidad(rs.getInt("cantidad"))
                      .estadoDetalleOrden(
                          EstadoDetalleOrden.getEstadoDetalleOrden(rs.getInt("detalle_estado")))
                      .build());
        }
        return null;
      };

  public OrdenRepository(JdbcTemplate template) {
    super(template);
  }

  public List<Orden> findall() {
    return template.query(FIND_ALL + SORT, rowMapper);
  }

  public Optional<Orden> findById(long id) {
    try {
      template.query(FIND_BY_ID, ordenCompletaRowMapper, id);
      Orden orden = ordenCompletaBuilder.get(id);
      ordenCompletaBuilder.clear();
      if (orden == null) {
        return Optional.empty();
      }
      return Optional.of(orden);
    } catch (EmptyResultDataAccessException e) {
      return Optional.empty();
    } catch (Exception e) {
      ordenCompletaBuilder.clear();
      e.printStackTrace();
      return Optional.empty();
    }
  }

  public Orden save(Orden orden) {
    if (orden.getId() <= 0) {
      long id = performInsert(INSERT, orden);
      orden.setId(id);
      guardarDetalleOrden(orden, id);
      return findById(orden.getId()).get();
    }

    template.update(UPDATE, orden.getMesaId(), orden.getId());
    guardarDetalleOrden(orden, orden.getId());
    return findById(orden.getId()).get();
  }

  public void terminar(Orden orden) {
    template.update(
        TERMINAR,
        orden.getEstado().ordinal(),
        orden.getTotalNeto(),
        orden.getImpuestos(),
        orden.getTotal(),
        orden.getId());
  }

  private void guardarDetalleOrden(Orden orden, long id) {
    // save detalle de la orden
    template.batchUpdate(
        INSERT_DETALLE,
        new BatchPreparedStatementSetter() {
          @Override
          public void setValues(PreparedStatement ps, int i) throws SQLException {
            ps.setLong(1, id);
            ps.setLong(2, orden.getDetalle().get(i).getItem().getItemMenuId());
            ps.setLong(3, orden.getDetalle().get(i).getCantidad());
            ps.setInt(4, orden.getDetalle().get(i).getEstadoDetalleOrden().ordinal());
          }

          @Override
          public int getBatchSize() {
            return orden.getDetalle().size();
          }
        });
  }

  @Override
  protected void prepareStatement(PreparedStatement ps, Orden orden) throws SQLException {
    ps.setLong(1, orden.getMesaId());
    ps.setLong(2, orden.getMesero().getId());
    ps.setInt(3, orden.getEstado().ordinal());
  }
}
