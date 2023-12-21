package com.rabbitmq.example.publisher;

import com.rabbitmq.example.configuration.RabbitMQConfig;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Publisher {

    private final AmqpTemplate rabbitTemplate;

    @Autowired
    public Publisher(AmqpTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendDirectMessage(String message, String routingKey) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.DIRECT_EXCHANGE, routingKey, message);
        System.out.println("Message sent: " + message);
    }

    public void sendTopicMessage(String message, String routingKey) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.TOPIC_EXCHANGE, routingKey, message);
        System.out.println("Message sent: " + message);
    }

    public void sendFanoutMessage(String message) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.FANOUT_EXCHANGE, null, message);
        System.out.println("Message sent: " + message);
    }

    public void sendHeaderMessage(String message, String type) {
        MessageProperties properties = new MessageProperties();
        properties.setHeader("type", type);

        rabbitTemplate.convertAndSend(RabbitMQConfig.HEADERS_EXCHANGE, null,message, msg -> {
            msg.getMessageProperties().setHeaders(properties.getHeaders());
            return msg;
        });
        System.out.println("Message sent: " + message);
    }
}