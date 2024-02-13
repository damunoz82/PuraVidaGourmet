package org.puravidatgourmet.api.controllers;

import java.net.URI;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.puravidatgourmet.api.domain.entity.MateriaPrima;
import org.puravidatgourmet.api.domain.pojo.MateriaPrimaPojo;
import org.puravidatgourmet.api.exceptions.ResourceNotFoundException;
import org.puravidatgourmet.api.mappers.MateriaPrimaMapper;
import org.puravidatgourmet.api.services.MateriaPrimaService;
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
@RequestMapping(path = "/materia-prima", produces = "application/json")
public class MateriaPrimaController extends BaseController {

  private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
  @Autowired private MateriaPrimaService materiaPrimaService;
  @Autowired private MateriaPrimaMapper mapper;

  @GetMapping
  @PreAuthorize("hasRole('ADMIN')")
  public List<MateriaPrimaPojo> getAll() {
    try {
      LOGGER.info("START: getAll");
      return mapper.toMateriaPrimaPojo(materiaPrimaService.getAllMateriaPrima()).stream()
          .sorted(Comparator.comparing((MateriaPrimaPojo::getNombre)))
          .collect(Collectors.toList());
    } finally {
      LOGGER.info("END: getAll");
    }
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public MateriaPrimaPojo get(@PathVariable long id) {
    try {
      LOGGER.info("START: get with Id: {}", id);
      Optional<MateriaPrima> resultado = materiaPrimaService.getMateriaPrima(id);
      return mapper.toMateriaPrimaPojo(
          resultado.orElseThrow(() -> new ResourceNotFoundException("Materia prima", "id", id)));
    } finally {
      LOGGER.info("END: get with Id: {}", id);
    }
  }

  @PostMapping
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<String> create(@RequestBody @Valid MateriaPrimaPojo materiaPrima) {
    try {
      LOGGER.info("START: create with Materia Prima: {}", materiaPrima);
      materiaPrimaService.validateSave(materiaPrima);

      MateriaPrima result =
          materiaPrimaService.saveMateriaPrima(mapper.toMateriaPrima(materiaPrima));

      return ResponseEntity.created(createLocation(String.valueOf(result.getId()))).build();
    } finally {
      LOGGER.info("END: create with Materia Prima: {}", materiaPrima);
    }
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<String> update(
      @PathVariable long id, @RequestBody MateriaPrimaPojo materiaPrima) {
    try {
      LOGGER.info("START: update with Materia Prima: {}", materiaPrima);
      materiaPrima.setId(id);
      materiaPrimaService.validateUpdate(materiaPrima);
      materiaPrimaService.saveMateriaPrima(mapper.toMateriaPrima(materiaPrima));
      URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
      return ResponseEntity.noContent().location(location).build();
    } finally {
      LOGGER.info("END: update with Materia Prima: {}", materiaPrima);
    }
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public void delete(@PathVariable long id) {
    try {
      LOGGER.info("START: delete with id: {}", id);
      materiaPrimaService.validateDelete(id);
      materiaPrimaService.deleteById(id);
    } finally {
      LOGGER.info("END: delete with id: {}", id);
    }
  }
}
