package org.puravidatgourmet.api.controllers;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.puravidatgourmet.api.domain.entity.TipoProducto;
import org.puravidatgourmet.api.domain.pojo.TipoProductoPojo;
import org.puravidatgourmet.api.exceptions.ResourceNotFoundException;
import org.puravidatgourmet.api.mappers.TipoProductoMapper;
import org.puravidatgourmet.api.services.TipoProductoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

  private static final Logger LOGGER = LoggerFactory.getLogger(TipoProductoController.class);
  @Autowired private TipoProductoService tipoProductoService;

  @Autowired private TipoProductoMapper mapper;

  @GetMapping
  @PreAuthorize("hasRole('ADMIN')")
  public List<TipoProductoPojo> getAll() {
    try {
      LOGGER.info("START: getAll");
      return mapper.toTipoProductoPojo(tipoProductoService.getAll());

    } finally {
      LOGGER.info("END: getAll");
    }
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public TipoProductoPojo get(@PathVariable long id) {
    try {
      LOGGER.info("START: get with id: {}", id);
      Optional<TipoProducto> result = tipoProductoService.getTipoProducto(id);
      return mapper.toTipoProductoPojo(
          result.orElseThrow(() -> new ResourceNotFoundException("Tipo Producto", "id", id)));
    } finally {
      LOGGER.info("END: get with id: {}", id);
    }
  }

  @PostMapping
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<String> create(@RequestBody @Valid TipoProductoPojo tipoProducto) {
    try {
      LOGGER.info("START: create with id: {}", tipoProducto);
      tipoProductoService.validateSave(tipoProducto);

      TipoProducto result =
          tipoProductoService.saveTipoProducto(mapper.toTipoProducto(tipoProducto));

      return ResponseEntity.created(createLocation(String.valueOf(result.getId()))).build();
    } finally {
      LOGGER.info("END: create with id: {}", tipoProducto);
    }
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<String> update(
      @PathVariable long id, @RequestBody TipoProductoPojo tipoProducto) {
    try {
      LOGGER.info("START: Update with id: {}, and Tipo Producto: {}", id, tipoProducto);

      // check exists.
      if (tipoProductoService.getTipoProducto(id).isEmpty()) {
        throw new ResourceNotFoundException("Tipo de Producto", "id", id);
      }

      tipoProducto.setId(id);

      tipoProductoService.validateUpdate(tipoProducto);

      tipoProductoService.saveTipoProducto(mapper.toTipoProducto(tipoProducto));

      URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
      return ResponseEntity.noContent().location(location).build();
    } finally {
      LOGGER.info("END: Update with id: {}, and Tipo Producto: {}", id, tipoProducto);
    }
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public void delete(@PathVariable long id) {
    try {
      LOGGER.info("START: delete with id: {}", id);
      tipoProductoService.validateDelete(id);
      tipoProductoService.deleteById(id);
    } finally {
      LOGGER.info("END: delete with id: {}", id);
    }
  }
}
