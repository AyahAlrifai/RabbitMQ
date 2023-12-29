package com.rabbitmq.example.controller;

import com.rabbitmq.example.publisher.Publisher;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * RabbitMQController.
 *
 * @author Ayah Refai
 * @since 12/19/2023
 */
@RestController
public class RabbitMQController {

    public final Publisher publisher;

    @Autowired
    public RabbitMQController(Publisher publisher) {
        this.publisher = publisher;
    }

    @GetMapping("/direct-exchange")
    public void directExchange(@PathParam("message") final String message,
                               @PathParam("routingKey") final String routingKey) {
        this.publisher.sendDirectMessage(message, routingKey);
    }

    @GetMapping("/topic-exchange")
    public void topicExchange(@PathParam("message") final String message,
                              @PathParam("routingKey") final String routingKey) {
        this.publisher.sendTopicMessage(message, routingKey);
    }

    @GetMapping("/fanout-exchange")
    public void fanoutExchange(@PathParam("message") final String message) {
        this.publisher.sendFanoutMessage(message);
    }

    @GetMapping("/header-exchange")
    public void headerExchange(@PathParam("message") final String message,
                               @PathParam("type") final String type) {
        this.publisher.sendHeaderMessage(message, type);
    }

    @GetMapping("/header-exchange-x-match")
    public void headerXMatchExchange(@PathParam("message") final String message,
                                     @PathParam("type") final String type,
                                     @PathParam("count") final String count) {
        this.publisher.sendHeaderXMatchMessage(message, type, count);
    }
}
