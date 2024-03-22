package org.puravidagourmet.restaurante.db.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import org.puravidagourmet.restaurante.domain.entity.ItemMenu;
import org.puravidagourmet.restaurante.domain.entity.Menu;
import org.puravidagourmet.restaurante.domain.entity.SeccionMenu;
import org.puravidagourmet.restaurante.domain.entity.Usuario;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class MenuRepository extends BaseRepository<Menu> {

  // MENU
  private final String FIND_ALL =
      "select m.id, m.nombre, m.temporada, m.descripcion, m.usu_registra, u.name as urName, u.email as urEmail, m.fecha_registro, "
          + "m.usu_modifica, u2.name as umName, u2.email as umEmail,  m.fecha_modifica, m.estado  from menu m "
          + "join usuario u on m.usu_registra = u.id "
          + "left join usuario u2 on m.usu_modifica = u2.id ";
  private final String FIND_BY_NAME = FIND_ALL + "where m.nombre=?";
  private final String SORT = "order by m.nombre";
  private final String FIND_BY_ID =
      "select m.id, m.nombre, m.temporada, m.descripcion, m.usu_registra, u.name as urName, u.email as urEmail, m.fecha_registro, "
          + "m.usu_modifica, u2.name as umName, u2.email as umEmail,  m.fecha_modifica, m.estado, "
          + "sm.seccion_id, sm.nombre as nombreSeccion, im.item_menu_id , im.nombre_comercial, im.descripcion as itemDescripcion, im.precio_venta from menu m "
          + "join usuario u on m.usu_registra = u.id "
          + "left join usuario u2 on m.usu_modifica = u2.id "
          + "join seccion_menu sm on m.id = sm.menu_id "
          + "join secciones_item_menu sim on sm.seccion_id = sim.seccion_id "
          + "join item_menu im on sim.item_id = im.item_menu_id where m.id=?";
  private final String INSERT_MENU =
      "insert into menu (nombre, temporada, descripcion, usu_registra, fecha_registro, estado) "
          + "values (?, ?, ?, ?, CURRENT_TIMESTAMP, ?)";

  private final String UPDATE_MENU =
      "update menu set nombre=?, temporada=?, descripcion=?, usu_modifica=?, fecha_modifica=current_timestamp, estado=? where id=?";

  //  private final String DELETE_RECETA = "delete from receta where id=?";

  // ITEM MENU
  private final String FIND_ALL_ITEM_MENU =
      "select im.item_menu_id, im.receta_id, im.nombre_comercial, im.descripcion, im.precio_venta from item_menu im ";
  private final String IM_SORT = "order by im.nombre_comercial";
  private final String FIND_ITEM_MENU_BY_ID = FIND_ALL_ITEM_MENU + "where im.item_menu_id=?";
  private final String FIND_ITEM_MENU_BY_NOMBRE =
      FIND_ALL_ITEM_MENU + "where im.nombre_comercial=?";

  private final String INSERT_ITEM_MENU =
      "insert into item_menu (receta_id, nombre_comercial, descripcion, precio_venta) values (?, ?, ?, ?)";
  private final String UPDATE_ITEM_MENU =
      "update item_menu set receta_id=?, nombre_comercial=?, descripcion=?, precio_venta=? where item_menu_id=?";
  private final String DELETE_ITEM_MENU = "delete from item_menu where item_menu_id=?";

  // SECCION
  private final String INSERT_SECCION = "insert into seccion_menu (nombre, menu_id) values (?, ?)";
  private final String UPDATE_SECCION =
      "update seccion_menu set nombre=? where seccion_id=? and menu_id=?";
  private final String DELETE_SECCION = "delete from seccion_menu where seccion_id=? and menu_id=?";

  // SECCION / ITEM MENU
  private final String INSERT_SECCION_ITEM =
      "insert into secciones_item_menu (seccion_id, item_id) values (?, ?)";
  private final String DELETE_SECCION_ITEM = "delete from secciones_item_menu where seccion_id=?";

  private final RowMapper<ItemMenu> itemMenuRowMapper =
      (rs, rowNum) -> {
        return ItemMenu.builder()
            .itemMenuId(rs.getLong("item_menu_id"))
            .recetaId(rs.getLong("receta_id"))
            .nombreComercial(rs.getString("nombre_comercial"))
            .descripcion(rs.getString("descripcion"))
            .precioVenta(rs.getFloat("precio_venta"))
            .build();
      };

  private final RowMapper<Menu> menuRowMapper =
      (rs, rowNum) -> {
        Usuario registra =
            Usuario.builder()
                .id(rs.getInt("usu_registra"))
                .name(rs.getString("urName"))
                .email(rs.getString("urEmail"))
                .build();
        Usuario modifica =
            Usuario.builder()
                .id(rs.getInt("usu_modifica"))
                .name(rs.getString("umName"))
                .email(rs.getString("umEmail"))
                .build();
        return Menu.builder()
            .id(rs.getLong("id"))
            .nombre(rs.getString("nombre"))
            .temporada(rs.getString("temporada"))
            .descripcion(rs.getString("descripcion"))
            .usuarioRegistra(registra)
            .fechaCreacion(rs.getTimestamp("fecha_registro"))
            .ususarioModifica(modifica)
            .fechaModificacion(rs.getTimestamp("fecha_modifica"))
            .secciones(new ArrayList<>())
            .menuEstado(rs.getInt("estado") == 1)
            .build();
      };

  private Map<Long, Menu> menuCompletoBuilder = new HashMap<>();
  private final RowMapper<Menu> menuCompleteRowMapper =
      (rs, rowNum) -> {
        if (menuCompletoBuilder.isEmpty()) {
          Usuario registra =
              Usuario.builder()
                  .id(rs.getInt("usu_registra"))
                  .name(rs.getString("urName"))
                  .email(rs.getString("urEmail"))
                  .build();
          Usuario modifica =
              Usuario.builder()
                  .id(rs.getInt("usu_modifica"))
                  .name(rs.getString("umName"))
                  .email(rs.getString("umEmail"))
                  .build();
          ItemMenu itemMenu =
              ItemMenu.builder()
                  .itemMenuId(rs.getLong("item_menu_id"))
                  .nombreComercial(rs.getString("nombre_comercial"))
                  .descripcion(rs.getString("itemDescripcion"))
                  .precioVenta(rs.getFloat("precio_venta"))
                  .build();
          SeccionMenu seccionMenu =
              SeccionMenu.builder()
                  .seccionId(rs.getLong("seccion_id"))
                  .nombre(rs.getString("nombreSeccion"))
                  .itemMenus(new ArrayList<>(List.of(itemMenu)))
                  .build();
          Menu menu =
              Menu.builder()
                  .id(rs.getLong("id"))
                  .nombre(rs.getString("nombre"))
                  .temporada(rs.getString("temporada"))
                  .descripcion(rs.getString("descripcion"))
                  .usuarioRegistra(registra)
                  .fechaCreacion(rs.getTimestamp("fecha_registro"))
                  .ususarioModifica(modifica)
                  .fechaModificacion(rs.getTimestamp("fecha_modifica"))
                  .secciones(new ArrayList<>(List.of(seccionMenu)))
                  .menuEstado(rs.getInt("estado") == 1)
                  .build();
          menuCompletoBuilder.put(menu.getId(), menu);

        } else {
          Menu menu = menuCompletoBuilder.get(rs.getLong("id"));

          ItemMenu itemMenu =
              ItemMenu.builder()
                  .itemMenuId(rs.getLong("item_menu_id"))
                  .nombreComercial(rs.getString("nombre_comercial"))
                  .descripcion(rs.getString("itemDescripcion"))
                  .precioVenta(rs.getFloat("precio_venta"))
                  .build();

          long seccionId = rs.getLong("seccion_id");
          Optional<SeccionMenu> optionalSeccionMenu =
              menu.getSecciones().stream().filter(i -> i.getSeccionId() == seccionId).findFirst();
          if (optionalSeccionMenu.isEmpty()) {
            SeccionMenu seccionMenu =
                SeccionMenu.builder()
                    .seccionId(rs.getLong("seccion_id"))
                    .nombre(rs.getString("nombreSeccion"))
                    .itemMenus(new ArrayList<>(List.of(itemMenu)))
                    .build();
            menu.getSecciones().add(seccionMenu);

          } else {
            SeccionMenu seccionMenu = optionalSeccionMenu.get();
            seccionMenu.getItemMenus().add(itemMenu);
          }
        }
        return null;
      };

  public MenuRepository(JdbcTemplate template) {
    super(template);
  }

  @Override
  protected void prepareStatement(PreparedStatement ps, Menu menu) throws SQLException {
    ps.setString(1, menu.getNombre());
    ps.setString(2, menu.getTemporada());
    ps.setString(3, menu.getDescripcion());
    ps.setLong(4, menu.getUsuarioRegistra().getId());
    ps.setInt(5, menu.isMenuEstado() ? 1 : 0);
  }

  public List<Menu> findAll() {
    return template.query(FIND_ALL + SORT, menuRowMapper);
  }

  public List<ItemMenu> findAllItemMenu() {
    return template.query(FIND_ALL_ITEM_MENU + IM_SORT, itemMenuRowMapper);
  }

  public Optional<Menu> findByNombre(String nombre) {
    try {
      Menu result = template.queryForObject(FIND_BY_NAME, menuRowMapper, nombre);
      return Optional.of(result);
    } catch (EmptyResultDataAccessException e) {
      return Optional.empty();
    }
  }

  public Optional<Menu> findById(long id) {
    try {
      template.query(FIND_BY_ID, menuCompleteRowMapper, id);
      Menu result = menuCompletoBuilder.get(id);
      menuCompletoBuilder.clear();
      if (result == null) {
        return Optional.empty();
      }
      return Optional.of(result);
    } catch (EmptyResultDataAccessException e) {
      return Optional.empty();
    } catch (Exception e) {
      menuCompletoBuilder.clear();
      return Optional.empty();
    }
  }

  public Optional<ItemMenu> findItemMenuById(long id) {
    try {
      ItemMenu item = template.queryForObject(FIND_ITEM_MENU_BY_ID, itemMenuRowMapper, id);
      return Optional.of(item);
    } catch (EmptyResultDataAccessException e) {
      return Optional.empty();
    }
  }

  public Optional<ItemMenu> findItemMenuByNombre(String nombre) {
    try {
      ItemMenu item = template.queryForObject(FIND_ITEM_MENU_BY_NOMBRE, itemMenuRowMapper, nombre);
      return Optional.of(item);
    } catch (EmptyResultDataAccessException e) {
      return Optional.empty();
    }
  }

  public Menu save(Menu menu) {
    if (menu.getId() <= 0) {
      // save menu
      long id = performInsert(INSERT_MENU, menu);
      menu.setId(id);

      saveSeccionAndItems(menu, id);

      return menu;

    } else {
      // update
      template.update(
          UPDATE_MENU,
          menu.getNombre(),
          menu.getTemporada(),
          menu.getDescripcion(),
          menu.getUsusarioModifica().getId(),
          menu.isMenuEstado() ? 1 : 0,
          menu.getId());

      // clean up
      menu.getSecciones()
          .forEach(
              s -> {
                // delete items
                template.update(DELETE_SECCION_ITEM, s.getSeccionId());

                // delete seccion
                template.update(DELETE_SECCION, s.getSeccionId(), menu.getId());
              });

      // save new menu.
      saveSeccionAndItems(menu, menu.getId());
    }
    return menu;
  }

  private void saveSeccionAndItems(Menu menu, long id) {
    menu.getSecciones()
        .forEach(
            s -> {
              // save seccion
              KeyHolder keyHolder = new GeneratedKeyHolder();
              template.update(
                  con -> {
                    PreparedStatement ps =
                        con.prepareStatement(INSERT_SECCION, Statement.RETURN_GENERATED_KEYS);
                    ps.setString(1, s.getNombre());
                    ps.setLong(2, id);
                    return ps;
                  },
                  keyHolder);

              long idSeccion = (long) Objects.requireNonNull(keyHolder.getKeys()).get("seccion_id");

              // save seccion - item menu
              s.getItemMenus()
                  .forEach(
                      (si) -> template.update(INSERT_SECCION_ITEM, idSeccion, si.getItemMenuId()));
            });
  }

  public ItemMenu save(ItemMenu itemMenu) {
    if (itemMenu.getItemMenuId() <= 0) {
      KeyHolder keyHolder = new GeneratedKeyHolder();
      template.update(
          con -> {
            PreparedStatement ps =
                con.prepareStatement(INSERT_ITEM_MENU, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, itemMenu.getRecetaId());
            ps.setString(2, itemMenu.getNombreComercial());
            ps.setString(3, itemMenu.getDescripcion());
            ps.setFloat(4, itemMenu.getPrecioVenta());
            return ps;
          },
          keyHolder);
      long menuItemId = (long) Objects.requireNonNull(keyHolder.getKeys()).get("item_menu_id");
      itemMenu.setItemMenuId(menuItemId);

    } else {
      template.update(
          UPDATE_ITEM_MENU,
          itemMenu.getRecetaId(),
          itemMenu.getNombreComercial(),
          itemMenu.getDescripcion(),
          itemMenu.getPrecioVenta(),
          itemMenu.getItemMenuId());
    }

    return itemMenu;
  }

  public void delete(long id) {
    // delete secciones_item
    // delete seccion
    // delete menu
  }

  public void deleteItemMenu(long id) {
    template.update(DELETE_ITEM_MENU, id);
  }
}
