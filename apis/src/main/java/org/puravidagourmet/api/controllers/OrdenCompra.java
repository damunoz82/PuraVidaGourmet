package org.puravidagourmet.api.controllers;

import java.util.Optional;
import java.util.concurrent.ExecutionException;
import org.puravidagourmet.api.config.security.CurrentUser;
import org.puravidagourmet.api.config.security.UserPrincipal;
import org.puravidagourmet.api.domain.entity.Receta;
import org.puravidagourmet.api.services.RabbitMQService;
import org.puravidagourmet.api.services.RecetasServices;
import org.puravidagourmet.api.services.handlers.CocinaStompSessionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.messaging.WebSocketStompClient;

@RestController
@RequestMapping(path = "/orden-compra", produces = "application/json")
public class OrdenCompra extends BaseController {

  /** THIS IS A TEST CLASS. */
  @Autowired private RabbitMQService rabbitMQService;

  @Autowired private RecetasServices recetasServices;

  //  @Autowired private CocinaSockerConnector connector;

  @Autowired private WebSocketStompClient webSocketStompClient;

  @PostMapping
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<String> create(@CurrentUser UserPrincipal userPrincipal)
      throws ExecutionException, InterruptedException {

    Optional<Receta> receta = recetasServices.get(1);
    if (receta.isPresent()) {
      rabbitMQService.sendCocinaMessage(receta);
      rabbitMQService.sendBarMessage(receta);
      rabbitMQService.sendCompraMessage(receta);

      StompSessionHandler stompSessionHandler = new CocinaStompSessionHandler();
      StompSession stompSession =
          webSocketStompClient
              .connectAsync("ws://localhost:8080/ordenes-socket", stompSessionHandler)
              .get();
      stompSession.send("/app/cocina/test", "{ message: 'hola mundo #2' }");
      stompSession.disconnect();
    }
    return ResponseEntity.created(createLocation(String.valueOf(1))).build();
  }
}
