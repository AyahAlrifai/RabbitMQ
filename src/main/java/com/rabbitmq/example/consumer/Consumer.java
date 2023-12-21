package com.rabbitmq.example.consumer;

import com.rabbitmq.example.configuration.RabbitMQConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {

    @RabbitListener(queues = RabbitMQConfig.QUEUE_1)
    public void receiveMessage1(String message) {
        System.out.println("q1 ------------> Received message: " + message);
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_2)
    public void receiveMessage2(String message) {
        System.out.println("q2 ------------> Received message: " + message);
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_3)
    public void receiveMessage3(String message) {
        System.out.println("q3 ------------> Received message: " + message);
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_4)
    public void receiveMessage4(String message) {
        System.out.println("q4 ------------> Received message: " + message);
    }
}
