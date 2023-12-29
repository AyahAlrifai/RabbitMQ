package com.rabbitmq.example.consumer;

import com.rabbitmq.example.configuration.RabbitMQConfig;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Consumer.
 *
 * @author Ayah Refai
 * @since 12/19/2023
 */
@Component
public class Consumer {

    /**
     * Listening to q1.
     * @param message message
     */
    @RabbitListener(queues = RabbitMQConfig.QUEUE_1)
    public void receiveMessage1(final String message) {
        System.out.println("q1 ------------> Received Message: " + message);
    }

    /**
     * Listening to q2.
     * @param message message
     */
    @RabbitListener(queues = RabbitMQConfig.QUEUE_2)
    public void receiveMessage2(final String message) {
        System.out.println("q2 ------------> Received Message: " + message);
    }

    /**
     * Listening to q3.
     * @param message message
     */
    @RabbitListener(queues = RabbitMQConfig.QUEUE_3)
    public void receiveMessage3(final String message) {
        System.out.println("q3 ------------> Received Message: " + message);
    }

    /**
     * Listening to q4.
     * @param message message
     */
    @RabbitListener(queues = RabbitMQConfig.QUEUE_4)
    public void receiveMessage4(final String message) {
        System.out.println("q4 ------------> Received Message: " + message);
    }

    /**
     * Listening to q5, and reject message to pass it to dead letter exchange.
     * @param message message
     */
    @RabbitListener(queues = RabbitMQConfig.QUEUE_5)
    public void receiveMessage5(final String message) {
        System.out.println("q5 ------------> Received Message: " + message);
        System.out.println("q5 ------------> Reject Message");
        throw new AmqpRejectAndDontRequeueException("Message rejected");
    }

    /**
     * Listening to dead.letter.queue.
     * @param message message
     */
    @RabbitListener(queues = RabbitMQConfig.DEAD_LETTER_QUEUE)
    public void receiveMessageDeadLetter(final String message) {
        System.out.println("deadLetter ------------> Received Message: " + message);
    }
}
