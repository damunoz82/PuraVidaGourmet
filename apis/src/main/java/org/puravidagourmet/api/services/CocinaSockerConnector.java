package org.puravidagourmet.api.services;

import org.puravidagourmet.api.domain.entity.Receta;
import org.puravidagourmet.api.services.handlers.CocinaStompSessionHandler;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

@Service
public class CocinaSockerConnector {

  private CocinaStompSessionHandler cocinaStompSessionHandler;

  public CocinaSockerConnector() {
    cocinaStompSessionHandler = new CocinaStompSessionHandler();
    connect();
  }

  public void connect() {
    WebSocketClient webSocketClient = new StandardWebSocketClient();
    WebSocketStompClient stompClient = new WebSocketStompClient(webSocketClient);
    stompClient.setMessageConverter(new StringMessageConverter());
    String url = "ws://localhost:8080/ordenes-socket";
    stompClient.connect(url, cocinaStompSessionHandler);
  }

  public void sendMessage(Receta receta) {
    cocinaStompSessionHandler.sendMessage(receta);
  }
}
