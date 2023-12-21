package com.rabbitmq.example.controller;

import com.rabbitmq.example.publisher.Publisher;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RabbitMQController {

    public final Publisher publisher;

    @Autowired
    public RabbitMQController(Publisher publisher) {
        this.publisher = publisher;
    }

    @GetMapping("/direct-exchange")
    public void directExchange(@PathParam("message") String message,
                               @PathParam("routingKey") String routingKey) {
        this.publisher.sendDirectMessage(message,routingKey);
    }

    @GetMapping("/topic-exchange")
    public void topicExchange(@PathParam("message") String message,
                               @PathParam("routingKey") String routingKey) {
        this.publisher.sendTopicMessage(message,routingKey);
    }

    @GetMapping("/fanout-exchange")
    public void fanoutExchange(@PathParam("message") String message) {
        this.publisher.sendFanoutMessage(message);
    }

    @GetMapping("/header-exchange")
    public void headerExchange(@PathParam("message") String message,
                              @PathParam("type") String type) {
        this.publisher.sendHeaderMessage(message,type);
    }
}
