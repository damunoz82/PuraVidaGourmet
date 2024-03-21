package org.puravidagourmet.restaurante.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

  @Autowired private AppProperties appProperties;

  @Bean
  public Queue getBarQueue() {
    return new Queue(appProperties.getRabbitMQ().getBarQueue());
  }

  @Bean
  public Queue getCocinaQueue() {
    return new Queue(appProperties.getRabbitMQ().getCocinaQueue());
  }

  @Bean
  public Queue getCompraQueue() {
    return new Queue(appProperties.getRabbitMQ().getCompraQueue());
  }

  @Bean
  public TopicExchange getExchange() {
    return new TopicExchange(appProperties.getRabbitMQ().getExchange());
  }

  @Bean
  public Binding bindingCocinaQueue() {
    return BindingBuilder.bind(getCocinaQueue())
        .to(getExchange())
        .with(appProperties.getRabbitMQ().getCocinaKey());
  }

  @Bean
  public Binding bindingBarQueue() {
    return BindingBuilder.bind(getBarQueue())
        .to(getExchange())
        .with(appProperties.getRabbitMQ().getBarKey());
  }

  @Bean
  public Binding bindingCompraQueue() {
    return BindingBuilder.bind(getCompraQueue())
        .to(getExchange())
        .with(appProperties.getRabbitMQ().getCompraKey());
  }

  // converts object to json
  @Bean
  public MessageConverter converter() {
    return new Jackson2JsonMessageConverter();
  }

  // sets the converter to the rabbit template.
  @Bean
  public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
    RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
    rabbitTemplate.setMessageConverter(converter());
    return rabbitTemplate;
  }
}
