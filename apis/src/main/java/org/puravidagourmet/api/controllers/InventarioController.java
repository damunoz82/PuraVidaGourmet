package org.puravidagourmet.api.controllers;

import static org.puravidagourmet.api.exceptions.codes.PuraVidaErrorCodes.INVENT_REC001;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.puravidagourmet.api.config.security.CurrentUser;
import org.puravidagourmet.api.config.security.UserPrincipal;
import org.puravidagourmet.api.db.repository.UsuarioRepository;
import org.puravidagourmet.api.domain.entity.Inventario;
import org.puravidagourmet.api.domain.enums.EstadoInventario;
import org.puravidagourmet.api.domain.pojo.InventarioPojo;
import org.puravidagourmet.api.exceptions.PuraVidaExceptionHandler;
import org.puravidagourmet.api.mappers.InventarioMapper;
import org.puravidagourmet.api.services.InventarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/inventario")
public class InventarioController extends BaseController {

  private final InventarioService inventarioService;

  private final UsuarioRepository usuarioRepository;

  private final InventarioMapper mapper;

  public InventarioController(
      InventarioService inventarioService,
      UsuarioRepository usuarioRepository,
      InventarioMapper mapper) {
    this.inventarioService = inventarioService;
    this.usuarioRepository = usuarioRepository;
    this.mapper = mapper;
  }

  @GetMapping
  @PreAuthorize("hasRole ('ADMIN')")
  public List<InventarioPojo> getAll() {
    return mapper.toInventarioPojo(inventarioService.getAll());
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasRole ('ADMIN')")
  public InventarioPojo get(@PathVariable long id) {
    return mapper.toInventarioPojo(
        inventarioService
            .getById(id)
            .orElseThrow(() -> new PuraVidaExceptionHandler(INVENT_REC001, id)));
  }

  @PostMapping
  @PreAuthorize("hasRole ('ADMIN')")
  public ResponseEntity<String> create(
      @RequestBody @Valid InventarioPojo inventarioPojo, @CurrentUser UserPrincipal userPrincipal) {
    Inventario inventario =
        inventarioService.createInventario(mapper.toInventario(inventarioPojo), userPrincipal);
    return ResponseEntity.created(createLocation(String.valueOf(inventario.getId()))).build();
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<String> actualizar(
      @PathVariable long id,
      @RequestBody InventarioPojo inventario,
      @CurrentUser UserPrincipal userPrincipal) {
    inventario.setId(id);

    inventarioService.actualizar(mapper.toInventario(inventario), userPrincipal);

    URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
    return ResponseEntity.noContent().location(location).build();
  }

  @PostMapping("/{id}/terminado")
  @PreAuthorize(("hasRole ('ADMIN')"))
  public ResponseEntity<String> terminar(
      @PathVariable long id, @CurrentUser UserPrincipal userPrincipal) {
    inventarioService.cambiarEstado(id, userPrincipal, EstadoInventario.TERMINADO);

    URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
    return ResponseEntity.noContent().location(location).build();
  }

  @PostMapping("/{id}/abandonado")
  @PreAuthorize(("hasRole ('ADMIN')"))
  public ResponseEntity<String> abandonar(
      @PathVariable long id, @CurrentUser UserPrincipal userPrincipal) {
    inventarioService.cambiarEstado(id, userPrincipal, EstadoInventario.ABANDONADO);

    URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
    return ResponseEntity.noContent().location(location).build();
  }
}
