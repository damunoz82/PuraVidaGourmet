package org.puravidagourmet.admin.controllers;

import static org.puravidagourmet.admin.exceptions.codes.PuraVidaErrorCodes.DEP_REC002;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.puravidagourmet.admin.domain.entity.Departamento;
import org.puravidagourmet.admin.domain.pojo.DepartamentoPojo;
import org.puravidagourmet.admin.exceptions.PuraVidaExceptionHandler;
import org.puravidagourmet.admin.mappers.DepartamentoMapper;
import org.puravidagourmet.admin.services.DepartamentoService;
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
@RequestMapping(path = "/departamento", produces = "application/json")
public class DepartamentoController extends BaseController {

  private final DepartamentoService departamentoService;

  private final DepartamentoMapper mapper;

  public DepartamentoController(
      DepartamentoService departamentoService, DepartamentoMapper mapper) {
    this.departamentoService = departamentoService;
    this.mapper = mapper;
  }

  @GetMapping
  @PreAuthorize("hasRole('ADMIN')")
  public List<DepartamentoPojo> getAll() {
    return mapper.toDepartamentoPojo(departamentoService.getAll()).stream()
        .sorted(Comparator.comparing((DepartamentoPojo::getNombre)))
        .collect(Collectors.toList());
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public DepartamentoPojo get(@PathVariable long id) {
    Optional<Departamento> result = departamentoService.getDepartamento(id);
    return mapper.toDepartamentoPojo(
        result.orElseThrow(() -> new PuraVidaExceptionHandler(DEP_REC002, id)));
  }

  @PostMapping
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<String> create(@RequestBody @Valid DepartamentoPojo departamento) {
    departamento.setId(0);

    Departamento result = departamentoService.saveDepartamento(mapper.toDepartamento(departamento));

    return ResponseEntity.created(createLocation(String.valueOf(result.getId()))).build();
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<String> update(
      @PathVariable long id, @RequestBody DepartamentoPojo departamentoPojo) {
    departamentoPojo.setId(id);

    // check exists.
    if (departamentoService.getDepartamento(id).isEmpty()) {
      throw new PuraVidaExceptionHandler(DEP_REC002, id);
    }

    departamentoService.saveDepartamento(mapper.toDepartamento(departamentoPojo));

    URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
    return ResponseEntity.noContent().location(location).build();
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public void delete(@PathVariable long id) {
    departamentoService.deleteById(id);
  }
}
