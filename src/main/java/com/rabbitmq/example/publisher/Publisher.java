package com.rabbitmq.example.publisher;

import com.rabbitmq.example.configuration.RabbitMQConfig;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Publisher.
 *
 * @author Ayah Refai
 * @since 12/19/2023
 */
@Component
public class Publisher {

    private final AmqpTemplate rabbitTemplate;

    @Autowired
    public Publisher(final AmqpTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendDirectMessage(final String message, final String routingKey) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.DIRECT_EXCHANGE, routingKey, message);
        System.out.println("Message: " + message + " RoutingKey: " + routingKey);
    }

    public void sendTopicMessage(final String message, final String routingKey) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.TOPIC_EXCHANGE, routingKey, message);
        System.out.println("Message: " + message + " RoutingKey: " + routingKey);
    }

    public void sendFanoutMessage(final String message) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.FANOUT_EXCHANGE, null, message);
        System.out.println("Message: " + message);
    }

    public void sendHeaderMessage(final String message, final String type) {
        MessageProperties properties = new MessageProperties();
        properties.setHeader("type", type);

        rabbitTemplate.convertAndSend(RabbitMQConfig.HEADERS_EXCHANGE, null, message, msg -> {
            msg.getMessageProperties().setHeaders(properties.getHeaders());
            return msg;
        });
        System.out.println("Message: " + message + " Type: " + type);
    }

    public void sendHeaderXMatchMessage(final String message, final String type, final String count) {
        MessageProperties properties = new MessageProperties();
        if (type != null && !type.isBlank()) {
            properties.setHeader("type", type);
        }
        if (count != null &&!count.isBlank()) {
            properties.setHeader("count", count);
        }

        rabbitTemplate.convertAndSend(RabbitMQConfig.HEADERS_EXCHANGE_X_MATCH, null, message, msg -> {
            msg.getMessageProperties().setHeaders(properties.getHeaders());
            return msg;
        });
        System.out.println("Message: " + message + " Type: " + type + " Count: " + count);
    }

}
