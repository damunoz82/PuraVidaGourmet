package org.puravidagourmet.engine.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQConsumer {

  private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQConsumer.class);

  @RabbitListener(queues = {"${app.rabbitmq.cocinaQueue}"})
  public void cocinaConsume(String message) {
    LOGGER.info(String.format("Cocina -> Received message %s from queue", message));
  }

  @RabbitListener(queues = {"${app.rabbitmq.barQueue}"})
  public void barConsume(String message) {
    LOGGER.info(String.format("Bar -> Received message %s from queue", message));
  }

  @RabbitListener(queues = {"${app.rabbitmq.compraQueue}"})
  public void compraConsume(String message) {
    LOGGER.info(String.format("Compra -> Received message %s from queue", message));
  }
}
