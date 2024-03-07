package org.puravidagourmet.api.controllers;

import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import org.puravidagourmet.api.config.security.CurrentUser;
import org.puravidagourmet.api.config.security.UserPrincipal;
import org.puravidagourmet.api.db.repository.UsuarioRepository;
import org.puravidagourmet.api.domain.entity.Inventario;
import org.puravidagourmet.api.domain.enums.EstadoInventario;
import org.puravidagourmet.api.domain.pojo.InventarioPojo;
import org.puravidagourmet.api.exceptions.BadRequestException;
import org.puravidagourmet.api.exceptions.ResourceNotFoundException;
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
            .orElseThrow(() -> new ResourceNotFoundException("Inventario", "id", id)));
  }

  @PostMapping
  @PreAuthorize("hasRole ('ADMIN')")
  public ResponseEntity<String> create(
      @RequestBody @Valid InventarioPojo inventarioPojo, @CurrentUser UserPrincipal userPrincipal) {
    Inventario inventario = mapper.toInventario(inventarioPojo);
    inventario.setEstado(EstadoInventario.CREADO);
    inventario.setResponsable(
        usuarioRepository
            .findByEmail(userPrincipal.getName())
            .orElseThrow(() -> new BadRequestException("Usuario que registra no fue encontrado")));
    inventarioService.createInventario(inventario);
    return ResponseEntity.created(createLocation(String.valueOf(inventario.getId()))).build();
  }

  @PostMapping("/{id}/cancel")
  @PreAuthorize(("hasRole ('ADMIN')"))
  public ResponseEntity<String> cancel(
      @PathVariable long id, @CurrentUser UserPrincipal userPrincipal) {
    inventarioService.cancel(id, userPrincipal);

    URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
    return ResponseEntity.noContent().location(location).build();
  }
}
