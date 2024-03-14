package org.puravidagourmet.api.services;

import org.puravidagourmet.api.config.AppProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQService {

  @Autowired private AppProperties properties;

  private RabbitTemplate rabbitTemplate;

  public RabbitMQService(RabbitTemplate rabbitTemplate) {
    this.rabbitTemplate = rabbitTemplate;
  }

  public void sendCocinaMessage(Object message) {
    rabbitTemplate.convertAndSend(
        properties.getRabbitMQ().getExchange(), properties.getRabbitMQ().getCocinaKey(), message);
  }

  public void sendBarMessage(Object message) {
    rabbitTemplate.convertAndSend(
        properties.getRabbitMQ().getExchange(), properties.getRabbitMQ().getBarKey(), message);
  }

  public void sendCompraMessage(Object message) {
    rabbitTemplate.convertAndSend(
        properties.getRabbitMQ().getExchange(), properties.getRabbitMQ().getCompraKey(), message);
  }
}
