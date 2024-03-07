package org.puravidagourmet.api.controllers;

import java.net.URI;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.puravidagourmet.api.domain.entity.Producto;
import org.puravidagourmet.api.domain.pojo.ProductoPojo;
import org.puravidagourmet.api.exceptions.ResourceNotFoundException;
import org.puravidagourmet.api.mappers.ProductoMapper;
import org.puravidagourmet.api.services.ProductoService;
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
@RequestMapping(path = "/producto", produces = "application/json")
public class ProductoController extends BaseController {

  private static final Logger LOGGER = LoggerFactory.getLogger(ProductoController.class);
//  @Autowired
  private final ProductoService productoService;
//  @Autowired
  private final ProductoMapper mapper;

  public ProductoController(ProductoService productoService, ProductoMapper productoMapper) {
    this.productoService = productoService;
    this.mapper = productoMapper;
  }

  @GetMapping
  @PreAuthorize("hasRole('ADMIN')")
  public List<ProductoPojo> getAll() {
    try {
      LOGGER.info("START: getAll");
      return mapper.toProductoPojo(productoService.getAllProducto()).stream()
          .sorted(Comparator.comparing((ProductoPojo::getNombre)))
          .collect(Collectors.toList());
    } finally {
      LOGGER.info("END: getAll");
    }
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ProductoPojo get(@PathVariable long id) {
    try {
      LOGGER.info("START: get with Id: {}", id);
      Optional<Producto> resultado = productoService.getProductoById(id);
      return mapper.toProductoPojo(
          resultado.orElseThrow(() -> new ResourceNotFoundException("Materia prima", "id", id)));
    } finally {
      LOGGER.info("END: get with Id: {}", id);
    }
  }

  @PostMapping
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<String> create(@RequestBody @Valid ProductoPojo producto) {
    try {
      LOGGER.info("START: create with Producto: {}", producto);
      productoService.validateSave(producto);

      Producto result = productoService.saveProducto(mapper.toProducto(producto));

      return ResponseEntity.created(createLocation(String.valueOf(result.getId()))).build();
    } finally {
      LOGGER.info("END: create with Producto: {}", producto);
    }
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<String> update(@PathVariable long id, @Valid @RequestBody ProductoPojo producto) {
    try {
      LOGGER.info("START: update with Producto: {}", producto);

      // check exists.
      if (productoService.getProductoById(id).isEmpty()) {
        throw new ResourceNotFoundException("Producto", "id", id);
      }

      producto.setId(id);
      productoService.validateUpdate(producto);
      productoService.saveProducto(mapper.toProducto(producto));

      URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
      return ResponseEntity.noContent().location(location).build();
    } finally {
      LOGGER.info("END: update with Producto: {}", producto);
    }
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public void delete(@PathVariable long id) {
    try {
      LOGGER.info("START: delete with id: {}", id);
      productoService.validateDelete(id);
      productoService.deleteById(id);
    } finally {
      LOGGER.info("END: delete with id: {}", id);
    }
  }
}
