package org.puravidagourmet.admin.controllers;

import org.puravidagourmet.admin.domain.pojo.OrdenPojo;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

  @MessageMapping("/cocina/{roomId}")
  @SendTo("/cocina-topic/{roomId}")
  public OrdenPojo cocina(@DestinationVariable String roomId, OrdenPojo message) {
    System.out.println(message.toString());
    return new OrdenPojo(message.getMensaje());
  }
}
