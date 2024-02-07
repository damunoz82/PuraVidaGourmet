package org.puravidatgourmet.security.controllers;

import java.net.URI;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.puravidatgourmet.security.domain.entity.MateriaPrima;
import org.puravidatgourmet.security.domain.pojo.MateriaPrimaPojo;
import org.puravidatgourmet.security.exceptions.ResourceNotFoundException;
import org.puravidatgourmet.security.mappers.MateriaPrimaMapper;
import org.puravidatgourmet.security.services.MateriaPrimaService;
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
@RequestMapping(path = "/materia-prima", produces = "application/json")
public class MateriaPrimaController extends BaseController {

  @Autowired private MateriaPrimaService materiaPrimaService;

  @Autowired private MateriaPrimaMapper mapper;

  @GetMapping
  @PreAuthorize("hasRole('ADMIN')")
  public List<MateriaPrimaPojo> getAll() {
    return mapper.toMateriaPrimaPojo(materiaPrimaService.getAllMateriaPrima()).stream()
        .sorted(Comparator.comparing((MateriaPrimaPojo::getNombre)))
        .collect(Collectors.toList());
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public MateriaPrimaPojo get(@PathVariable long id) {
    Optional<MateriaPrima> resultado = materiaPrimaService.getMateriaPrima(id);
    return mapper.toMateriaPrimaPojo(
        resultado.orElseThrow(() -> new ResourceNotFoundException("Materia prima", "id", id)));
  }

  @PostMapping
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<String> create(@RequestBody @Valid MateriaPrimaPojo materiaPrima) {

    materiaPrimaService.validateSave(materiaPrima);

    MateriaPrima result = materiaPrimaService.saveMateriaPrima(mapper.toMateriaPrima(materiaPrima));

    return ResponseEntity.created(createLocation(String.valueOf(result.getId()))).build();
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<String> update(
      @PathVariable long id, @RequestBody MateriaPrimaPojo materiaPrima) {
    materiaPrima.setId(id);

    materiaPrimaService.validateUpdate(materiaPrima);

    materiaPrimaService.saveMateriaPrima(mapper.toMateriaPrima(materiaPrima));

    URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
    return ResponseEntity.noContent().location(location).build();
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public void delete(@PathVariable long id) {

    materiaPrimaService.validateDelete(id);
    materiaPrimaService.deleteById(id);
  }
}
