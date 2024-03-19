package org.puravidagourmet.api.controllers;

import static org.puravidagourmet.api.exceptions.codes.PuraVidaErrorCodes.DEP_REC002;
import static org.puravidagourmet.api.exceptions.codes.PuraVidaErrorCodes.MENU_REC001;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import org.puravidagourmet.api.config.security.CurrentUser;
import org.puravidagourmet.api.config.security.UserPrincipal;
import org.puravidagourmet.api.domain.entity.Menu;
import org.puravidagourmet.api.domain.pojo.MenuPojo;
import org.puravidagourmet.api.exceptions.PuraVidaExceptionHandler;
import org.puravidagourmet.api.mappers.MenuMapper;
import org.puravidagourmet.api.services.MenuServicio;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(path = "/menu", produces = "application/json")
public class MenuController extends BaseController {

  private final MenuServicio menuServicio;

  private final MenuMapper mapper;

  public MenuController(MenuServicio menuServicio, MenuMapper mapper) {
    this.menuServicio = menuServicio;
    this.mapper = mapper;
  }

  @GetMapping
  @PreAuthorize("hasRole('ADMIN')")
  public List<MenuPojo> getAll() {
    return mapper.toMenuPojo(menuServicio.findAll());
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public MenuPojo get(@PathVariable long id) {
    Optional<Menu> result = menuServicio.findById(id);
    return mapper.toMenuPojo(
        result.orElseThrow(() -> new PuraVidaExceptionHandler(MENU_REC001, id))); // fixme - code
  }

  @PostMapping
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<String> create(
      @RequestBody @Valid MenuPojo menu, @CurrentUser UserPrincipal userPrincipal) {
    menu.setId(0);

    Menu result = menuServicio.save(mapper.toMenu(menu), userPrincipal);

    return ResponseEntity.created(createLocation(String.valueOf(result.getId()))).build();
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<String> update(
      @PathVariable long id,
      @RequestBody MenuPojo menuPojo,
      @CurrentUser UserPrincipal userPrincipal) {
    menuPojo.setId(id);

    // check exists.
    if (menuServicio.findById(id).isEmpty()) {
      throw new PuraVidaExceptionHandler(DEP_REC002, id);
    }

    menuServicio.save(mapper.toMenu(menuPojo), userPrincipal);

    URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
    return ResponseEntity.noContent().location(location).build();
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public void delete(@PathVariable long id) {
    menuServicio.delete(id);
  }
}
