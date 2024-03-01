package org.puravidatgourmet.api.controllers;

import java.net.URI;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.puravidatgourmet.api.domain.entity.Departamento;
import org.puravidatgourmet.api.domain.pojo.DepartamentoPojo;
import org.puravidatgourmet.api.domain.pojo.ProductoPojo;
import org.puravidatgourmet.api.exceptions.ResourceNotFoundException;
import org.puravidatgourmet.api.mappers.DepartamentoMapper;
import org.puravidatgourmet.api.services.DepartamentoService;
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
@RequestMapping(path = "/departamento", produces = "application/json")
public class DepartamentoController extends BaseController {

  private static final Logger LOGGER = LoggerFactory.getLogger(DepartamentoController.class);
  @Autowired private DepartamentoService departamentoService;

  @Autowired private DepartamentoMapper mapper;

  @GetMapping
  @PreAuthorize("hasRole('ADMIN')")
  public List<DepartamentoPojo> getAll() {
    try {
      LOGGER.info("START: getAll");
      return mapper.toDepartamentoPojo(departamentoService.getAll()).stream()
              .sorted(Comparator.comparing((DepartamentoPojo::getNombre)))
              .collect(Collectors.toList());

    } finally {
      LOGGER.info("END: getAll");
    }
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public DepartamentoPojo get(@PathVariable long id) {
    try {
      LOGGER.info("START: get with id: {}", id);
      Optional<Departamento> result = departamentoService.getDepartamento(id);
      return mapper.toDepartamentoPojo(
          result.orElseThrow(() -> new ResourceNotFoundException("Departamento", "id", id)));
    } finally {
      LOGGER.info("END: get with id: {}", id);
    }
  }

  @PostMapping
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<String> create(@RequestBody @Valid DepartamentoPojo departamento) {
    try {
      LOGGER.info("START: create with id: {}", departamento);
      departamentoService.validateSave(departamento);

      Departamento result =
          departamentoService.saveDepartamento(mapper.toDepartamento(departamento));

      return ResponseEntity.created(createLocation(String.valueOf(result.getId()))).build();
    } finally {
      LOGGER.info("END: create with id: {}", departamento);
    }
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<String> update(
      @PathVariable long id, @RequestBody DepartamentoPojo departamentoPojo) {
    try {
      LOGGER.info("START: Update with id: {}, and Tipo Producto: {}", id, departamentoPojo);
      departamentoPojo.setId(id);

      // check exists.
      if (departamentoService.getDepartamento(id).isEmpty()) {
        throw new ResourceNotFoundException("Departamento", "id", id);
      }

      departamentoService.validateUpdate(departamentoPojo);

      departamentoService.saveDepartamento(mapper.toDepartamento(departamentoPojo));

      URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
      return ResponseEntity.noContent().location(location).build();
    } finally {
      LOGGER.info("END: Update with id: {}, and Tipo Producto: {}", id, departamentoPojo);
    }
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public void delete(@PathVariable long id) {
    try {
      LOGGER.info("START: delete with id: {}", id);
      departamentoService.validateDelete(id);
      departamentoService.deleteById(id);
    } finally {
      LOGGER.info("END: delete with id: {}", id);
    }
  }
}
