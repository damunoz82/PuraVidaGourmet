package org.puravidagourmet.restaurante.controllers;

import static org.puravidagourmet.restaurante.exceptions.codes.PuraVidaErrorCodes.ORD_001;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import org.puravidagourmet.restaurante.config.security.CurrentUser;
import org.puravidagourmet.restaurante.config.security.UserPrincipal;
import org.puravidagourmet.restaurante.domain.entity.Orden;
import org.puravidagourmet.restaurante.domain.pojo.OrdenPojo;
import org.puravidagourmet.restaurante.exceptions.PuraVidaExceptionHandler;
import org.puravidagourmet.restaurante.mappers.OrdenMapper;
import org.puravidagourmet.restaurante.services.OrdenService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(path = "/ordenes", produces = "application/json")
public class OrdenesController extends BaseController {

  private final OrdenService ordenService;
  private final OrdenMapper mapper;

  public OrdenesController(OrdenService ordenService, OrdenMapper mapper) {
    this.ordenService = ordenService;
    this.mapper = mapper;
  }

  @GetMapping
  @PreAuthorize("hasAnyRole('ADMIN','RESTAURANTE')")
  public List<OrdenPojo> getAll() {
    return mapper.toOrdenPojo(ordenService.findAll());
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasAnyRole('ADMIN','RESTAURANTE')")
  public OrdenPojo get(@PathVariable long id) {
    Optional<Orden> result = ordenService.findById(id);
    return mapper.toOrdenPojo(result.orElseThrow(() -> new PuraVidaExceptionHandler(ORD_001, id)));
  }

  @PostMapping
  @PreAuthorize("hasAnyRole('ADMIN','RESTAURANTE')")
  public ResponseEntity<String> create(
      @RequestBody @Valid OrdenPojo orden, @CurrentUser UserPrincipal user) {
    orden.setId(0);

    Orden result = ordenService.save(mapper.toOrden(orden), user);

    return ResponseEntity.created(createLocation(String.valueOf(result.getId()))).build();
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasAnyRole('ADMIN','RESTAURANTE')")
  public ResponseEntity<String> update(
      @PathVariable long id, @RequestBody @Valid OrdenPojo orden, @CurrentUser UserPrincipal user) {
    orden.setId(id);

    ordenService.save(mapper.toOrden(orden), user);

    URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
    return ResponseEntity.noContent().location(location).build();
  }

  @PostMapping("/{id}/terminado")
  @PreAuthorize("hasAnyRole('ADMIN','RESTAURANTE')")
  public ResponseEntity<String> terminar(@PathVariable long id, @CurrentUser UserPrincipal user) {
    ordenService.terminar(id, user);

    URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
    return ResponseEntity.noContent().location(location).build();
  }
}
