package org.puravidagourmet.restaurante.controllers;

import org.puravidagourmet.restaurante.payload.OrdenPayload;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

  @MessageMapping("/restaurante/{roomId}")
  @SendTo("/restaurante-topic/{roomId}")
  public OrdenPayload notify(@DestinationVariable String roomId, OrdenPayload message) {
    System.out.println(message.toString()); // fixme - remove.
    return message;
  }
}
