package org.puravidagourmet.api.services;

import static org.puravidagourmet.api.exceptions.codes.PuraVidaErrorCodes.*;

import java.util.List;
import java.util.Optional;
import org.puravidagourmet.api.config.security.UserPrincipal;
import org.puravidagourmet.api.db.repository.MenuRepository;
import org.puravidagourmet.api.db.repository.UsuarioRepository;
import org.puravidagourmet.api.domain.entity.ItemMenu;
import org.puravidagourmet.api.domain.entity.Menu;
import org.puravidagourmet.api.exceptions.PuraVidaExceptionHandler;
import org.springframework.stereotype.Service;

@Service
public class MenuServicio {

  private final MenuRepository menuRepository;

  private final UsuarioRepository usuarioRepository;

  public MenuServicio(MenuRepository menuRepository, UsuarioRepository usuarioRepository) {
    this.menuRepository = menuRepository;
    this.usuarioRepository = usuarioRepository;
  }

  public List<Menu> findAll() {
    return menuRepository.findAll();
  }

  public List<ItemMenu> findAllItemMenu() {
    return menuRepository.findAllItemMenu();
  }

  public Optional<Menu> findById(long id) {
    return menuRepository.findById(id);
  }

  public Optional<ItemMenu> findItemMenuById(long id) {
    return menuRepository.findItemMenuById(id);
  }

  public Menu save(Menu menu, UserPrincipal userPrincipal) {
    if (menu.getId() <= 0) {
      // evitar duplicados
      menuRepository
          .findByNombre(menu.getNombre())
          .ifPresent(
              e -> {
                throw new PuraVidaExceptionHandler(MENU_REC002);
              });

      // existen los item del menu
      menu.getSecciones()
          .forEach(
              s -> {
                s.getItemMenus()
                    .forEach(
                        i ->
                            menuRepository
                                .findItemMenuById(i.getItemMenuId())
                                .orElseThrow(() -> new PuraVidaExceptionHandler(ITEM_REC001)));
              });

      // assignar usuario
      menu.setUsuarioRegistra(
          usuarioRepository
              .findByEmail(userPrincipal.getName())
              .orElseThrow(() -> new PuraVidaExceptionHandler(USU_REC003)));

    } else {
      // update
      // avoid duplicates
      menuRepository
          .findByNombre(menu.getNombre())
          .ifPresent(
              m -> {
                if (m.getId() != menu.getId()) {
                  throw new PuraVidaExceptionHandler(MENU_REC002);
                }
              });

      // set usuario que modifica.
      menu.setUsusarioModifica(
          usuarioRepository
              .findByEmail(userPrincipal.getName())
              .orElseThrow(() -> new PuraVidaExceptionHandler(USU_REC003)));
    }

    // save
    menuRepository.save(menu);

    return menu;
  }

  public ItemMenu save(ItemMenu itemMenu) {
    if (itemMenu.getItemMenuId() <= 0) {
      // prevent duplicates
      menuRepository
          .findItemMenuByNombre(itemMenu.getNombreComercial())
          .ifPresent(e -> new PuraVidaExceptionHandler(ITEM_REC002));

    } else {
      // prevent duplicates
    }

    return menuRepository.save(itemMenu);
  }

  public void delete(long id) {
    menuRepository.delete(id);
  }

  public void deleteItemMenu(long id) {
    menuRepository.deleteItemMenu(id);
  }

  private void validateItemsExisten(Menu menu) {}
}
