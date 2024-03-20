package org.puravidagourmet.api.services.handlers;

import java.lang.reflect.Type;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.puravidagourmet.api.domain.entity.Receta;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.stereotype.Component;

@Component
public class CocinaStompSessionHandler extends StompSessionHandlerAdapter {

  private Logger logger = LogManager.getLogger(CocinaStompSessionHandler.class);

  //  private StompSession session;

  @Override
  public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
    logger.info("New session established : " + session.getSessionId());
    session.subscribe("/cocina-topic/test", this);
    logger.info("Subscribed to /cocina-topic/test");
    //    this.session = session;
  }

  //  public void sendMessage(Receta receta) {
  //    session.send("/app/cocina/test", receta);
  //    logger.info("Message sent to websocket server");
  //  }

  @Override
  public void handleException(
      StompSession session,
      StompCommand command,
      StompHeaders headers,
      byte[] payload,
      Throwable exception) {
    logger.error("Got an exception", exception);
  }

  @Override
  public Type getPayloadType(StompHeaders headers) {
    return Receta.class;
  }

  @Override
  public void handleFrame(StompHeaders headers, Object payload) {
    Receta msg = (Receta) payload;
    logger.info("Received : " + msg.toString());
  }
}
