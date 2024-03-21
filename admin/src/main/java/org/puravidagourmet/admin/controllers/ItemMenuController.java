package org.puravidagourmet.admin.controllers;

import static org.puravidagourmet.admin.exceptions.codes.PuraVidaErrorCodes.*;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import org.puravidagourmet.admin.domain.entity.ItemMenu;
import org.puravidagourmet.admin.domain.pojo.ItemMenuPojo;
import org.puravidagourmet.admin.exceptions.PuraVidaExceptionHandler;
import org.puravidagourmet.admin.mappers.ItemMenuMapper;
import org.puravidagourmet.admin.services.MenuServicio;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(path = "/item-menu", produces = "application/json")
public class ItemMenuController extends BaseController {

  private final MenuServicio menuServicio;

  private final ItemMenuMapper mapper;

  public ItemMenuController(MenuServicio menuServicio, ItemMenuMapper mapper) {
    this.menuServicio = menuServicio;
    this.mapper = mapper;
  }

  @GetMapping
  @PreAuthorize("hasRole('ADMIN')")
  public List<ItemMenuPojo> getAll() {
    return mapper.toItemMenuPojo(menuServicio.findAllItemMenu());
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ItemMenuPojo get(@PathVariable long id) {
    Optional<ItemMenu> result = menuServicio.findItemMenuById(id);
    return mapper.toItemMenuPojo(
        result.orElseThrow(() -> new PuraVidaExceptionHandler(ITEM_REC001, id)));
  }

  @PostMapping
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<String> create(@RequestBody @Valid ItemMenuPojo item) {
    item.setItemMenuId(0);

    ItemMenu result = menuServicio.save(mapper.toItemMenu(item));

    return ResponseEntity.created(createLocation(String.valueOf(result.getItemMenuId()))).build();
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<String> update(@PathVariable long id, @RequestBody ItemMenuPojo item) {
    item.setItemMenuId(id);

    // check exists.
    if (menuServicio.findItemMenuById(id).isEmpty()) {
      throw new PuraVidaExceptionHandler(ITEM_REC001, id);
    }

    menuServicio.save(mapper.toItemMenu(item));

    URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
    return ResponseEntity.noContent().location(location).build();
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public void delete(@PathVariable long id) {
    menuServicio.deleteItemMenu(id);
  }
}
