package org.puravidagourmet.admin.services;

import org.puravidagourmet.admin.config.AppProperties;
import org.puravidagourmet.admin.domain.entity.Receta;
import org.puravidagourmet.admin.domain.pojo.OrdenPojo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQService {

  private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQService.class);
  @Autowired private AppProperties properties;
  @Autowired private SimpMessagingTemplate template;
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

  // POC -> receive messages goes in another module.
  @RabbitListener(queues = {"${app.rabbitmq.cocinaQueue}"})
  public void cocinaConsume(Receta message) {
    LOGGER.info(String.format("Cocina -> Received message %s from queue", message.toString()));

    OrdenPojo ordenPojo = new OrdenPojo();
    ordenPojo.setMensaje(message.toString());

    this.template.convertAndSend("/cocina-topic/test", ordenPojo);
  }
}
