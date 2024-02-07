package org.puravidatgourmet.security.controllers;

import java.net.URI;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.puravidatgourmet.security.config.security.CurrentUser;
import org.puravidatgourmet.security.config.security.UserPrincipal;
import org.puravidatgourmet.security.db.repository.UsuarioRepository;
import org.puravidatgourmet.security.domain.User;
import org.puravidatgourmet.security.domain.entity.Receta;
import org.puravidatgourmet.security.domain.pojo.RecetaPojo;
import org.puravidatgourmet.security.exceptions.ResourceNotFoundException;
import org.puravidatgourmet.security.mappers.RecetaMapper;
import org.puravidatgourmet.security.services.RecetasServices;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/receta")
public class RecetaController extends BaseController {

  @Autowired private RecetasServices recetasServices;

  @Autowired private UsuarioRepository usuarioRepository;

  @Autowired private RecetaMapper mapper;

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
    Optional<User> user = usuarioRepository.findByEmail(userPrincipal.getEmail());

    recetasServices.validateSave(receta);

    Receta recetaFull = mapper.toReceta(receta);
    recetaFull.setUsuarioRegistra(
        user.orElseThrow(() -> new RuntimeException("Creating user not found")));
    recetasServices.saveReceta(recetaFull);
    Receta result = recetasServices.saveReceta(recetaFull);

    return ResponseEntity.created(createLocation(String.valueOf(result.getId()))).build();
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<String> update(
      @PathVariable long id,
      @RequestBody RecetaPojo receta,
      @CurrentUser UserPrincipal userPrincipal) {
    receta.setId(id);
    Optional<User> user = usuarioRepository.findByEmail(userPrincipal.getEmail());

    recetasServices.validateUpdate(receta);

    Receta recetaFull = mapper.toReceta(receta);
    recetaFull.setUsuarioRegistra(
        user.orElseThrow(() -> new RuntimeException("Creating user not found")));
    recetasServices.saveReceta(recetaFull);

    URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
    return ResponseEntity.noContent().location(location).build();
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public void delete(@PathVariable long id) {

    recetasServices.validateDelete(id);
    recetasServices.delete(id);
  }
}
