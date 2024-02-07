package org.puravidatgourmet.security.controllers;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.puravidatgourmet.security.domain.entity.RecetaCategoria;
import org.puravidatgourmet.security.domain.pojo.RecetaCategoriaPojo;
import org.puravidatgourmet.security.exceptions.ResourceNotFoundException;
import org.puravidatgourmet.security.mappers.RecetaCategoriaMapper;
import org.puravidatgourmet.security.services.RecetaCategoriaService;
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
@RequestMapping(path = "/receta-categoria", produces = "application/json")
public class RecetaCategoriaController extends BaseController {

  @Autowired private RecetaCategoriaService recetaCategoriaService;

  @Autowired private RecetaCategoriaMapper mapper;

  @GetMapping
  @PreAuthorize("hasRole('ADMIN')")
  public List<RecetaCategoriaPojo> getAll() {
    return mapper.toRecetaCategoriaPojo(recetaCategoriaService.getAll());
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public RecetaCategoriaPojo get(@PathVariable long id) {
    Optional<RecetaCategoria> result = recetaCategoriaService.getRecetaCategoria(id);
    return mapper.toRecetaCategoriaPojo(
        result.orElseThrow(() -> new ResourceNotFoundException("Categoria", "id", id)));
  }

  @PostMapping
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<String> create(@RequestBody @Valid RecetaCategoriaPojo recetaCategoria) {

    recetaCategoriaService.validateSave(recetaCategoria);

    RecetaCategoria result =
        recetaCategoriaService.saveRecetaCategoria(mapper.toCategoriaReceta(recetaCategoria));

    return ResponseEntity.created(createLocation(String.valueOf(result.getId()))).build();
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<String> update(
      @PathVariable long id, @RequestBody RecetaCategoriaPojo recetaCategoriaPojo) {
    recetaCategoriaPojo.setId(id);

    recetaCategoriaService.validateUpdate(recetaCategoriaPojo);

    recetaCategoriaService.saveRecetaCategoria(mapper.toCategoriaReceta(recetaCategoriaPojo));

    URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
    return ResponseEntity.noContent().location(location).build();
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public void delete(@PathVariable long id) {

    recetaCategoriaService.validateDelete(id);
    recetaCategoriaService.deleteById(id);
  }
}
