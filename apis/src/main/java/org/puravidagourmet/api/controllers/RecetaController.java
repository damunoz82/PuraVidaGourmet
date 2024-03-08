package org.puravidagourmet.api.controllers;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.puravidagourmet.api.config.security.CurrentUser;
import org.puravidagourmet.api.config.security.UserPrincipal;
import org.puravidagourmet.api.domain.entity.Receta;
import org.puravidagourmet.api.domain.pojo.RecetaPojo;
import org.puravidagourmet.api.exceptions.ResourceNotFoundException;
import org.puravidagourmet.api.mappers.RecetaMapper;
import org.puravidagourmet.api.services.RecetasServices;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/receta")
public class RecetaController extends BaseController {

  private final RecetasServices recetasServices;

  private final RecetaMapper mapper;

  public RecetaController(RecetasServices recetasServices, RecetaMapper mapper) {
    this.recetasServices = recetasServices;
    this.mapper = mapper;
  }

  @GetMapping
  @PreAuthorize("hasRole('ADMIN')")
  public List<RecetaPojo> getAll(@RequestParam(required = false) String categoria) {
    return mapper.toRecetas(
        recetasServices.getAll(categoria).stream()
            .sorted(Comparator.comparing(Receta::getNombre))
            .collect(Collectors.toList()));
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public RecetaPojo get(@PathVariable long id) {
    Optional<Receta> result = recetasServices.get(id);
    return mapper.toRecetaPojo(
        result.orElseThrow(() -> new ResourceNotFoundException("Receta", "id", id)));
  }

  @PostMapping
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<String> create(
      @RequestBody @Valid RecetaPojo receta, @CurrentUser UserPrincipal userPrincipal) {
    receta.setId(0);
    Receta entity = mapper.toReceta(receta);
    Receta result = recetasServices.saveReceta(entity, userPrincipal);

    return ResponseEntity.created(createLocation(String.valueOf(result.getId()))).build();
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<String> update(
      @PathVariable long id,
      @RequestBody RecetaPojo receta,
      @CurrentUser UserPrincipal userPrincipal) {
    receta.setId(id);
    Receta entity = mapper.toReceta(receta);
    recetasServices.saveReceta(entity, userPrincipal);

    URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
    return ResponseEntity.noContent().location(location).build();
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public void delete(@PathVariable long id) {
    recetasServices.delete(id);
  }
}
