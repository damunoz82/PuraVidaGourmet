package org.puravidagourmet.api.controllers;

import java.util.Optional;
import org.puravidagourmet.api.config.security.CurrentUser;
import org.puravidagourmet.api.config.security.UserPrincipal;
import org.puravidagourmet.api.domain.entity.Receta;
import org.puravidagourmet.api.services.RabbitMQService;
import org.puravidagourmet.api.services.RecetasServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/orden-compra", produces = "application/json")
public class OrdenCompra extends BaseController {

  @Autowired private RabbitMQService rabbitMQService;

  @Autowired private RecetasServices recetasServices;

  @PostMapping
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<String> create(@CurrentUser UserPrincipal userPrincipal) {

    Optional<Receta> receta = recetasServices.get(1);
    if (receta.isPresent()) {
      rabbitMQService.sendCocinaMessage(receta);
      rabbitMQService.sendBarMessage(receta);
      rabbitMQService.sendCompraMessage(receta);
    }
    return ResponseEntity.created(createLocation(String.valueOf(1))).build();
  }
}
