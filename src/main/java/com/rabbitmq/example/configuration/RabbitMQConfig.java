package com.rabbitmq.example.configuration;

import org.springframework.amqp.core.*;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;

@Configuration
public class RabbitMQConfig {
    public static final String DIRECT_EXCHANGE = "ayah.direct.exchange";
    public static final String TOPIC_EXCHANGE = "ayah.topic.exchange";
    public static final String FANOUT_EXCHANGE = "ayah.fanout.exchange";
    public static final String HEADERS_EXCHANGE = "ayah.headers.exchange";

    public static final String QUEUE_1 = "q1";
    public static final String QUEUE_2 = "q2";
    public static final String QUEUE_3 = "q3";
    public static final String QUEUE_4 = "q4";

    /**
     * header binding to q1 when argument `type` equal `message`
     * header binding to q2 when argument `type` equal `email`
     * header binding to q3 when argument `type` equal `notification`
     * header binding to q4 when argument `type` equal `sms`
     *
     * @return Binding
     */
    private static List<Binding> headersBinding() {
        ArrayList<Binding> bindings = new ArrayList<>();

        Map<String, Object> arguments1 = new HashMap<>();
        arguments1.put("type", "message");
        bindings.add(new Binding(QUEUE_1,
                Binding.DestinationType.QUEUE,
                HEADERS_EXCHANGE,
                "", // it is not mandatory in rabbitMQ but should not be null in java
                arguments1));

        Map<String, Object> arguments2 = new HashMap<>();
        arguments2.put("type", "email");
        bindings.add(new Binding(QUEUE_2,
                Binding.DestinationType.QUEUE,
                HEADERS_EXCHANGE,
                "",
                arguments2));

        Map<String, Object> arguments3 = new HashMap<>();
        arguments3.put("type", "notification");
        bindings.add(new Binding(QUEUE_3,
                Binding.DestinationType.QUEUE,
                HEADERS_EXCHANGE,
                "",
                arguments3));

        Map<String, Object> arguments4 = new HashMap<>();
        arguments4.put("type", "sms");
        bindings.add(new Binding(QUEUE_4,
                Binding.DestinationType.QUEUE,
                HEADERS_EXCHANGE,
                "",
                arguments4));

        return bindings;
    }


    /**
     * topic binding to q1 when routing key match ayah.log1.*
     * topic binding to q2 when routing key match ayah.log2.*
     * topic binding to q3 when routing key match ayah.*
     * topic binding to q4 when routing key match *.log2.*
     * where exchange is ayah.topic.exchange
     *
     * @return Binding
     */
    private static List<Binding> topicBinding() {
        ArrayList<Binding> bindings = new ArrayList<>();
        bindings.add(new Binding(QUEUE_1,
                Binding.DestinationType.QUEUE,
                TOPIC_EXCHANGE,
                "ayah.log1.*",
                null));
        bindings.add(new Binding(QUEUE_2,
                Binding.DestinationType.QUEUE,
                TOPIC_EXCHANGE,
                "ayah.log2.*",
                null));
        bindings.add(new Binding(QUEUE_3,
                Binding.DestinationType.QUEUE,
                TOPIC_EXCHANGE,
                "ayah.*.*",
                null));
        bindings.add(new Binding(QUEUE_4,
                Binding.DestinationType.QUEUE,
                TOPIC_EXCHANGE,
                "*.log2.*",
                null));
        return bindings;
    }

    /**
     * fanout binding to q1,q2,q3, qnd q4
     * where exchange is ayah.fanout.exchange
     *
     * @return Binding
     */
    private static List<Binding> fanoutBinding() {
        ArrayList<Binding> bindings = new ArrayList<>();
        bindings.add(new Binding(QUEUE_1,
                Binding.DestinationType.QUEUE,
                FANOUT_EXCHANGE,
                "",
                null));
        bindings.add(new Binding(QUEUE_2,
                Binding.DestinationType.QUEUE,
                FANOUT_EXCHANGE,
                "",
                null));
        bindings.add(new Binding(QUEUE_3,
                Binding.DestinationType.QUEUE,
                FANOUT_EXCHANGE,
                "",
                null));
        bindings.add(new Binding(QUEUE_4,
                Binding.DestinationType.QUEUE,
                FANOUT_EXCHANGE,
                "",
                null));
        return bindings;
    }

    /**
     * direct binding to q1 when routing key is log1
     * direct binding to q2 when routing key is log2
     * direct binding to q3 when routing key is log3
     * direct binding to q4 when routing key is log4
     * where exchange is ayah.direct.exchange
     *
     * @return Binding
     */
    private static List<Binding> directBinding() {
        ArrayList<Binding> bindings = new ArrayList<>();
        bindings.add(new Binding(QUEUE_1,
                Binding.DestinationType.QUEUE,
                DIRECT_EXCHANGE,
                "log1",
                null));
        bindings.add(new Binding(QUEUE_2,
                Binding.DestinationType.QUEUE,
                DIRECT_EXCHANGE,
                "log2",
                null));
        bindings.add(new Binding(QUEUE_3,
                Binding.DestinationType.QUEUE,
                DIRECT_EXCHANGE,
                "log3",
                null));
        bindings.add(new Binding(QUEUE_4,
                Binding.DestinationType.QUEUE,
                DIRECT_EXCHANGE,
                "log4",
                null));
        return bindings;
    }

    @Bean
    public Declarables topicExchangeBindings() {
        ArrayList<Declarable> declarable = new ArrayList<>();
        declarable.add(new Queue(QUEUE_1, true));
        declarable.add(new Queue(QUEUE_2, true));
        declarable.add(new Queue(QUEUE_3, true));
        declarable.add(new Queue(QUEUE_4, true));
        declarable.add(new DirectExchange(DIRECT_EXCHANGE, true, false));
        declarable.add(new TopicExchange(TOPIC_EXCHANGE, true, false));
        declarable.add(new FanoutExchange(FANOUT_EXCHANGE, true, false));
        declarable.add(new HeadersExchange(HEADERS_EXCHANGE, true, false));
        declarable.addAll(directBinding());
        declarable.addAll(topicBinding());
        declarable.addAll(headersBinding());
        declarable.addAll(fanoutBinding());
        return new Declarables(declarable);
    }
}
