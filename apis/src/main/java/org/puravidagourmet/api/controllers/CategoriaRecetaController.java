package org.puravidagourmet.api.controllers;

import static org.puravidagourmet.api.exceptions.codes.PuraVidaErrorCodes.CAT_REC002;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.puravidagourmet.api.domain.entity.CategoriaReceta;
import org.puravidagourmet.api.domain.pojo.CategoriaRecetaPojo;
import org.puravidagourmet.api.exceptions.PuraVidaExceptionHandler;
import org.puravidagourmet.api.mappers.CategoriaRecetaMapper;
import org.puravidagourmet.api.services.CategoriaRecetaService;
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
@RequestMapping(path = "/categoria-receta", produces = "application/json")
public class CategoriaRecetaController extends BaseController {

  private final CategoriaRecetaService categoriaRecetaService;

  private final CategoriaRecetaMapper mapper;

  public CategoriaRecetaController(
      CategoriaRecetaService categoriaRecetaService, CategoriaRecetaMapper mapper) {
    this.categoriaRecetaService = categoriaRecetaService;
    this.mapper = mapper;
  }

  @GetMapping
  @PreAuthorize("hasRole('ADMIN')")
  public List<CategoriaRecetaPojo> getAll() {
    return mapper.toCategoriaRecetaPojo(categoriaRecetaService.getAll()).stream()
        .sorted(Comparator.comparing((CategoriaRecetaPojo::getNombre)))
        .collect(Collectors.toList());
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public CategoriaRecetaPojo get(@PathVariable long id) {
    Optional<CategoriaReceta> result = categoriaRecetaService.getCategoriaReceta(id);
    return mapper.toCategoriaRecetaPojo(
        result.orElseThrow(() -> new PuraVidaExceptionHandler(CAT_REC002, id)));
  }

  @PostMapping
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<String> create(
      @RequestBody @Valid CategoriaRecetaPojo categoriaRecetaPojo) {
    categoriaRecetaPojo.setId(0);

    CategoriaReceta result =
        categoriaRecetaService.saveCategoriaReceta(mapper.toCategoriaReceta(categoriaRecetaPojo));

    return ResponseEntity.created(createLocation(String.valueOf(result.getId()))).build();
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<String> update(
      @PathVariable long id, @RequestBody CategoriaRecetaPojo categoriaRecetaPojo) {
    categoriaRecetaPojo.setId(id);

    // check exists.
    if (categoriaRecetaService.getCategoriaReceta(id).isEmpty()) {
        throw new PuraVidaExceptionHandler(CAT_REC002, id);
    }

    categoriaRecetaService.saveCategoriaReceta(mapper.toCategoriaReceta(categoriaRecetaPojo));

    URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
    return ResponseEntity.noContent().location(location).build();
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public void delete(@PathVariable long id) {
    categoriaRecetaService.deleteById(id);
  }
}
