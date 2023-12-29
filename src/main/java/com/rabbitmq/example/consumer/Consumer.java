package com.rabbitmq.example.consumer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.example.configuration.RabbitMQConfig;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class Consumer {

    @RabbitListener(queues = RabbitMQConfig.QUEUE_1)
    public void receiveMessage1(String message) {
        System.out.println("q1 ------------> Received Message: " + message);
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_2)
    public void receiveMessage2(String message) {
        System.out.println("q2 ------------> Received Message: " + message);
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_3)
    public void receiveMessage3(String message) {
        System.out.println("q3 ------------> Received Message: " + message);
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_4)
    public void receiveMessage4(String message) {
        System.out.println("q4 ------------> Received Message: " + message);
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_5)
    public void receiveMessage5(String message) {
        System.out.println("q5 ------------> Received Message: " + message);
        System.out.println("q5 ------------> Reject Message");
        throw new AmqpRejectAndDontRequeueException("Message rejected");
    }

    @RabbitListener(queues = RabbitMQConfig.DEAD_LETTER_QUEUE)
    public void receiveMessageDeadLetter(String message) {
        System.out.println("deadLetter ------------> Received Message: " + message);
    }
}
