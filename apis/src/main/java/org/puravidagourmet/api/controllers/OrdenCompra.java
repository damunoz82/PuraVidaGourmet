package org.puravidagourmet.api.controllers;

import java.util.concurrent.ExecutionException;
import org.puravidagourmet.api.config.security.CurrentUser;
import org.puravidagourmet.api.config.security.UserPrincipal;
import org.puravidagourmet.api.domain.pojo.OrdenPojo;
import org.puravidagourmet.api.services.RabbitMQService;
import org.puravidagourmet.api.services.RecetasServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/orden-compra", produces = "application/json")
public class OrdenCompra extends BaseController {

  /** THIS IS A TEST CLASS. */
  @Autowired private RabbitMQService rabbitMQService;

  @Autowired private RecetasServices recetasServices;

  @Autowired private SimpMessagingTemplate template;

  @PostMapping
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<String> create(@CurrentUser UserPrincipal userPrincipal)
      throws ExecutionException, InterruptedException {

    OrdenPojo ordenPojo = new OrdenPojo();
    ordenPojo.setMensaje("Hola Mundo");

    this.template.convertAndSend("/cocina-topic/test", ordenPojo);

    return ResponseEntity.created(createLocation(String.valueOf(1))).build();
  }
}
