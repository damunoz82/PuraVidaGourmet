package org.puravidagourmet.api.controllers;

import static org.puravidagourmet.api.exceptions.codes.PuraVidaErrorCodes.TPROD_REC002;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.puravidagourmet.api.domain.entity.TipoProducto;
import org.puravidagourmet.api.domain.pojo.TipoProductoPojo;
import org.puravidagourmet.api.exceptions.PuraVidaExceptionHandler;
import org.puravidagourmet.api.mappers.TipoProductoMapper;
import org.puravidagourmet.api.services.TipoProductoService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(path = "/tipo-producto", produces = "application/json")
public class TipoProductoController extends BaseController {

  private final TipoProductoService tipoProductoService;

  private final TipoProductoMapper mapper;

  public TipoProductoController(
      TipoProductoService tipoProductoService, TipoProductoMapper mapper) {
    this.tipoProductoService = tipoProductoService;
    this.mapper = mapper;
  }

  @GetMapping
  @PreAuthorize("hasRole('ADMIN')")
  public List<TipoProductoPojo> getAll() {
    return mapper.toTipoProductoPojo(tipoProductoService.getAll()).stream()
        .sorted(Comparator.comparing((TipoProductoPojo::getNombre)))
        .collect(Collectors.toList());
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public TipoProductoPojo get(@PathVariable long id) {
    Optional<TipoProducto> result = tipoProductoService.getTipoProducto(id);
    return mapper.toTipoProductoPojo(
        result.orElseThrow(() -> new PuraVidaExceptionHandler(TPROD_REC002, id)));
  }

  @PostMapping
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<String> create(@RequestBody @Valid TipoProductoPojo tipoProducto) {
    tipoProducto.setId(0);

    TipoProducto result = tipoProductoService.saveTipoProducto(mapper.toTipoProducto(tipoProducto));

    return ResponseEntity.created(createLocation(String.valueOf(result.getId()))).build();
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<String> update(
      @PathVariable long id, @RequestBody TipoProductoPojo tipoProducto) {

    // check exists.
    if (tipoProductoService.getTipoProducto(id).isEmpty()) {
      throw new PuraVidaExceptionHandler(TPROD_REC002, id);
    }

    tipoProducto.setId(id);

    tipoProductoService.saveTipoProducto(mapper.toTipoProducto(tipoProducto));

    URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
    return ResponseEntity.noContent().location(location).build();
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public void delete(@PathVariable long id) {
    tipoProductoService.deleteById(id);
  }
}
