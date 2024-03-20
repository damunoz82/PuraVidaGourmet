package org.puravidagourmet.api.controllers;

import static org.puravidagourmet.api.exceptions.codes.PuraVidaErrorCodes.MESA_RECC002;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import org.puravidagourmet.api.domain.entity.Mesa;
import org.puravidagourmet.api.domain.pojo.MesaPojo;
import org.puravidagourmet.api.exceptions.PuraVidaExceptionHandler;
import org.puravidagourmet.api.mappers.MesaMapper;
import org.puravidagourmet.api.services.MesaService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(path = "restaurante-mesa", produces = "application/json")
public class MesaController extends BaseController {

  private final MesaService mesaService;

  private final MesaMapper mapper;

  public MesaController(MesaService mesaService, MesaMapper mapper) {
    this.mesaService = mesaService;
    this.mapper = mapper;
  }

  @GetMapping
  @PreAuthorize("hasRole('ADMIN')")
  public List<MesaPojo> getAll() {
    return mapper.toMesaPojoList(mesaService.findAll());
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public MesaPojo get(@PathVariable long id) {
    Optional<Mesa> result = mesaService.getByid(id);
    return mapper.toMesaPojo(
        result.orElseThrow(() -> new PuraVidaExceptionHandler(MESA_RECC002, id)));
  }

  @PostMapping
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<String> create(@RequestBody @Valid MesaPojo mesa) {
    mesa.setId(0);

    Mesa result = mesaService.saveMesa(mapper.toMesa(mesa));

    return ResponseEntity.created(createLocation(String.valueOf(result.getId()))).build();
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<String> update(@PathVariable long id, @RequestBody MesaPojo mesaPojo) {
    mesaPojo.setId(id);

    // check exists.
    if (mesaService.getByid(id).isEmpty()) {
      throw new PuraVidaExceptionHandler(MESA_RECC002, id);
    }

    mesaService.saveMesa(mapper.toMesa(mesaPojo));

    URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
    return ResponseEntity.noContent().location(location).build();
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public void delete(@PathVariable long id) {
    mesaService.delete(id);
  }
}
