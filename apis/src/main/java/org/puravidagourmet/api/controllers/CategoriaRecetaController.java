package org.puravidagourmet.api.controllers;

import java.net.URI;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.puravidagourmet.api.domain.entity.CategoriaReceta;
import org.puravidagourmet.api.domain.pojo.CategoriaRecetaPojo;
import org.puravidagourmet.api.domain.pojo.DepartamentoPojo;
import org.puravidagourmet.api.exceptions.ResourceNotFoundException;
import org.puravidagourmet.api.mappers.CategoriaRecetaMapper;
import org.puravidagourmet.api.services.CategoriaRecetaService;
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
@RequestMapping(path = "/categoria-receta", produces = "application/json")
public class CategoriaRecetaController extends BaseController {

  private static final Logger LOGGER = LoggerFactory.getLogger(CategoriaRecetaController.class);
  @Autowired private CategoriaRecetaService categoriaRecetaService;

  @Autowired private CategoriaRecetaMapper mapper;

  @GetMapping
  @PreAuthorize("hasRole('ADMIN')")
  public List<CategoriaRecetaPojo> getAll() {
    try {
      LOGGER.info("START: getAll");
      return mapper.toCategoriaRecetaPojo(categoriaRecetaService.getAll()).stream()
              .sorted(Comparator.comparing((CategoriaRecetaPojo::getNombre)))
              .collect(Collectors.toList());

    } finally {
      LOGGER.info("END: getAll");
    }
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public CategoriaRecetaPojo get(@PathVariable long id) {
    try {
      LOGGER.info("START: get with id: {}", id);
      Optional<CategoriaReceta> result = categoriaRecetaService.getCategoriaReceta(id);
      return mapper.toCategoriaRecetaPojo(
          result.orElseThrow(() -> new ResourceNotFoundException("Categoria Receta", "id", id)));
    } finally {
      LOGGER.info("END: get with id: {}", id);
    }
  }

  @PostMapping
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<String> create(
      @RequestBody @Valid CategoriaRecetaPojo categoriaRecetaPojo) {
    try {
      LOGGER.info("START: create with categoria receta: {}", categoriaRecetaPojo);
      categoriaRecetaService.validateSave(categoriaRecetaPojo);

      CategoriaReceta result =
          categoriaRecetaService.saveCategoriaReceta(mapper.toCategoriaReceta(categoriaRecetaPojo));

      return ResponseEntity.created(createLocation(String.valueOf(result.getId()))).build();
    } finally {
      LOGGER.info("END: create with categoria receta: {}", categoriaRecetaPojo);
    }
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<String> update(
      @PathVariable long id, @RequestBody CategoriaRecetaPojo categoriaRecetaPojo) {
    try {
      LOGGER.info("START: Update with id: {}, and categoria receta: {}", id, categoriaRecetaPojo);
      categoriaRecetaPojo.setId(id);

      // check exists.
      if (categoriaRecetaService.getCategoriaReceta(id).isEmpty()) {
        throw new ResourceNotFoundException("Categoria", "id", id);
      }

      categoriaRecetaService.validateUpdate(categoriaRecetaPojo);

      categoriaRecetaService.saveCategoriaReceta(mapper.toCategoriaReceta(categoriaRecetaPojo));

      URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
      return ResponseEntity.noContent().location(location).build();
    } finally {
      LOGGER.info("END: Update with id: {}, and categoria receta: {}", id, categoriaRecetaPojo);
    }
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public void delete(@PathVariable long id) {
    try {
      LOGGER.info("START: delete with id: {}", id);
      categoriaRecetaService.validateDelete(id);
      categoriaRecetaService.deleteById(id);
    } finally {
      LOGGER.info("END: delete with id: {}", id);
    }
  }
}
