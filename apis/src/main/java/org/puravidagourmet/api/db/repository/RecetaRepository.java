package org.puravidagourmet.api.db.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.puravidagourmet.api.domain.User;
import org.puravidagourmet.api.domain.entity.CategoriaReceta;
import org.puravidagourmet.api.domain.entity.Receta;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class RecetaRepository extends BaseRepository<Receta> {

  private final String FIND_ALL =
      "select r.id, r.impuestos, r.alergenos, r.costo_porcion, r.costo_receta, r.elaboracion, r.equipo_necesario, r.fecha_modificacion, r.fecha_registro, r.margen_ganancia, "
          + "r.nombre, r.numero_porciones, r.precio_de_venta, r.tamanio_porcion, r.temperatura_de_servido, r.tiempo_coccion, r.tiempo_preparacion, "
          + "r.categoria_receta_id, cr.nombre as nombreCategoria, r.usuario_modifica_id, up.name as modifica_name, up.email as modifica_email, r.usuario_registra_id, ur.name as registra_name, ur.email as registra_email  from recetas r "
          + "join categoria_receta cr on r.categoria_receta_id = cr.id "
          + "join usuario ur on r.usuario_registra_id = ur.id "
          + "left join usuario up on r.usuario_modifica_id  = up.id";

  private final String FIND_BY_NOMBRE = FIND_ALL + " where r.nombre = ?";

  private final String FIND_BY_ID = FIND_ALL + " where r.id = ?";

  private final String FIND_BY_CATEGORIA = FIND_ALL + " where cr.nombre = ?";

  private final String CREATE_RECETA =
      "insert into recetas (alergenos, costo_porcion, costo_receta, elaboracion, equipo_necesario, fecha_modificacion, "
          + "fecha_registro, margen_ganancia, "
          + "nombre, numero_porciones, precio_de_venta, tamanio_porcion, temperatura_de_servido, tiempo_coccion, "
          + "tiempo_preparacion, "
          + "categoria_receta_id, usuario_modifica_id, usuario_registra_id, impuestos) values (?, ?, ?, ?, ?, "
          + "null, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, null, ?, ?)";

  private final String UPDATE_RECETA =
      "update recetas set impuestos=?, alergenos=?, costo_porcion=?, costo_receta=?, elaboracion=?, equipo_necesario=?, "
          + "fecha_modificacion=?, margen_ganancia=?, nombre=?, numero_porciones=?, precio_de_venta=?, tamanio_porcion=?, "
          + "temperatura_de_servido=?, tiempo_coccion=?, tiempo_preparacion=?, "
          + "categoria_receta_id=?, usuario_modifica_id=? where id=?";

  private final String DELETE = "delete from recetas where id = ?";

  private final RowMapper<Receta> rowMapper =
      (rs, rowNum) -> {
        User usuarioModifica =
            User.builder()
                .id(rs.getLong("usuario_modifica_id"))
                .name(rs.getString("modifica_name"))
                .email(rs.getString("modifica_email"))
                .build();
        User usuarioRegistra =
            User.builder()
                .id(rs.getLong("usuario_registra_id"))
                .name(rs.getString("registra_name"))
                .email(rs.getString("registra_email"))
                .build();
        CategoriaReceta categoriaReceta =
            CategoriaReceta.builder()
                .id(rs.getLong("categoria_receta_id"))
                .nombre(rs.getString("nombreCategoria"))
                .build();
        return Receta.builder()
            .id(rs.getInt("id"))
            .impuestos(rs.getFloat("impuestos"))
            .alergenos(rs.getString("alergenos"))
            .costoPorcion(rs.getFloat("costo_porcion"))
            .costoReceta(rs.getFloat("costo_receta"))
            .elaboracion(rs.getString("elaboracion"))
            .equipoNecesario(rs.getString("equipo_necesario"))
            .fechaModificacion(rs.getDate("fecha_modificacion"))
            .fechaRegistro(rs.getDate("fecha_registro"))
            .margenGanancia(rs.getFloat("margen_ganancia"))
            .nombre(rs.getString("nombre"))
            .numeroPorciones(rs.getLong("numero_porciones"))
            .precioDeVenta(rs.getFloat("precio_de_venta"))
            .tamanioPorcion(rs.getLong("tamanio_porcion"))
            .temperaturaDeServido(rs.getLong("temperatura_de_servido"))
            .tiempoCoccion(rs.getLong("tiempo_coccion"))
            .tiempoPreparacion(rs.getLong("tiempo_preparacion"))
            .categoriaReceta(categoriaReceta)
            .usuarioModifica(usuarioModifica)
            .usuarioRegistra(usuarioRegistra)
            .ingredientes(new ArrayList<>())
            .build();
      };

  public RecetaRepository(JdbcTemplate template) {
    super(template);
  }

  @Override
  protected void prepareStatement(PreparedStatement ps, Receta receta) throws SQLException {
    ps.setString(1, receta.getAlergenos());
    ps.setFloat(2, receta.getCostoPorcion());
    ps.setFloat(3, receta.getCostoReceta());
    ps.setString(4, receta.getElaboracion());
    ps.setString(5, receta.getEquipoNecesario());
    ps.setDate(6, receta.getFechaRegistro());
    ps.setFloat(7, receta.getMargenGanancia());
    ps.setString(8, receta.getNombre());
    ps.setLong(9, receta.getNumeroPorciones());
    ps.setFloat(10, receta.getPrecioDeVenta());
    ps.setLong(11, receta.getTamanioPorcion());
    ps.setLong(12, receta.getTemperaturaDeServido());
    ps.setLong(13, receta.getTiempoCoccion());
    ps.setLong(14, receta.getTiempoPreparacion());
    ps.setLong(15, receta.getCategoriaReceta().getId());
    ps.setLong(16, receta.getUsuarioRegistra().getId());
    ps.setFloat(17, receta.getImpuestos());
  }

  public List<Receta> findAll() {
    return template.query(FIND_ALL, rowMapper);
  }

  public Optional<Receta> findByNombre(String nombre) {
    try {
      Receta receta = template.queryForObject(FIND_BY_NOMBRE, rowMapper, nombre);
      return Optional.ofNullable(receta);
    } catch (EmptyResultDataAccessException e) {
      return Optional.empty();
    }
  }

  public Optional<Receta> findById(long id) {
    try {
      Receta receta = template.queryForObject(FIND_BY_ID, rowMapper, id);
      return Optional.ofNullable(receta);
    } catch (EmptyResultDataAccessException e) {
      return Optional.empty();
    }
  }

  public List<Receta> findByCategoriaRecetaNombre(String categoria) {
    return template.query(FIND_BY_CATEGORIA, rowMapper, categoria);
  }

  public Receta save(Receta receta) {
    if (receta.getId() <= 0) {
      long id = performInsert(CREATE_RECETA, receta);
      receta.setId(id);
      return receta;
    }
    template.update(
        UPDATE_RECETA,
        receta.getImpuestos(),
        receta.getAlergenos(),
        receta.getCostoPorcion(),
        receta.getCostoReceta(),
        receta.getElaboracion(),
        receta.getEquipoNecesario(),
        receta.getFechaModificacion(),
        receta.getMargenGanancia(),
        receta.getNombre(),
        receta.getNumeroPorciones(),
        receta.getPrecioDeVenta(),
        receta.getTamanioPorcion(),
        receta.getTemperaturaDeServido(),
        receta.getTiempoCoccion(),
        receta.getTiempoPreparacion(),
        receta.getCategoriaReceta().getId(),
        receta.getUsuarioModifica().getId(),
        receta.getId());
    return receta;
  }

  public void delete(long id) {
    template.update(DELETE, id);
  }
}
