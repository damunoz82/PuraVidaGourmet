package org.puravidagourmet.api.controllers;

import static org.puravidagourmet.api.exceptions.codes.PuraVidaErrorCodes.PROD_REC002;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.puravidagourmet.api.domain.entity.Producto;
import org.puravidagourmet.api.domain.pojo.ProductoPojo;
import org.puravidagourmet.api.exceptions.PuraVidaExceptionHandler;
import org.puravidagourmet.api.mappers.ProductoMapper;
import org.puravidagourmet.api.services.ProductoService;
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

  private final ProductoService productoService;

  private final ProductoMapper mapper;

  public ProductoController(ProductoService productoService, ProductoMapper productoMapper) {
    this.productoService = productoService;
    this.mapper = productoMapper;
  }

  @GetMapping
  @PreAuthorize("hasRole('ADMIN')")
  public List<ProductoPojo> getAll() {
    return mapper.toProductoPojo(productoService.getAllProducto()).stream()
        .sorted(Comparator.comparing((ProductoPojo::getNombre)))
        .collect(Collectors.toList());
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ProductoPojo get(@PathVariable long id) {
    Optional<Producto> resultado = productoService.getProductoById(id);
    return mapper.toProductoPojo(
        resultado.orElseThrow(() -> new PuraVidaExceptionHandler(PROD_REC002, id)));
  }

  @PostMapping
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<String> create(@RequestBody @Valid ProductoPojo producto) {
    producto.setId(0);

    Producto result = productoService.saveProducto(mapper.toProducto(producto));

    return ResponseEntity.created(createLocation(String.valueOf(result.getId()))).build();
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<String> update(
      @PathVariable long id, @Valid @RequestBody ProductoPojo producto) {

    // check exists.
    if (productoService.getProductoById(id).isEmpty()) {
      throw new PuraVidaExceptionHandler(PROD_REC002, id);
    }

    producto.setId(id);
    productoService.saveProducto(mapper.toProducto(producto));

    URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
    return ResponseEntity.noContent().location(location).build();
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public void delete(@PathVariable long id) {
    productoService.deleteById(id);
  }
}
