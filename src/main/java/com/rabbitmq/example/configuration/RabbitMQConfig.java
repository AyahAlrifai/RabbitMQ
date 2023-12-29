package com.rabbitmq.example.configuration;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class RabbitMQConfig {

    public static final String DIRECT_EXCHANGE = "ayah.direct.exchange";
    public static final String TOPIC_EXCHANGE = "ayah.topic.exchange";
    public static final String FANOUT_EXCHANGE = "ayah.fanout.exchange";
    public static final String HEADERS_EXCHANGE = "ayah.headers.exchange";
    public static final String HEADERS_EXCHANGE_X_MATCH = "ayah.headers.match.exchange";
    public static final String DEAD_LETTER_EXCHANGE = "ayah.dead.letter.exchange";

    public static final String QUEUE_1 = "q1";
    public static final String QUEUE_2 = "q2";
    public static final String QUEUE_3 = "q3";
    public static final String QUEUE_4 = "q4";
    public static final String QUEUE_5 = "q5";

    public static final String DEAD_LETTER_QUEUE = "dead.letter.queue";
    public static final String DEAD_LETTER_ROUTING_KEY = "dead_letter";

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
     * header binding to q1 when argument `type` equal `message` or `count equal 1
     * header binding to q2 when argument `type` equal `message` and `count` equal 1
     * header binding to q3 when argument `type` equal `message`
     * header binding to q4 when argument `count` equal 1
     *
     * @return Binding
     */
    private static List<Binding> headersXMatchBinding() {
        ArrayList<Binding> bindings = new ArrayList<>();

        Map<String, Object> arguments1 = new HashMap<>();
        arguments1.put("x-match", "any");
        arguments1.put("type", "message");
        arguments1.put("count", "1");
        bindings.add(new Binding(QUEUE_1,
                Binding.DestinationType.QUEUE,
                HEADERS_EXCHANGE_X_MATCH,
                "", // it is not mandatory in rabbitMQ but should not be null in java
                arguments1));

        Map<String, Object> arguments2 = new HashMap<>();
        arguments2.put("x-match", "all");
        arguments2.put("type", "message");
        arguments2.put("count", "1");
        bindings.add(new Binding(QUEUE_2,
                Binding.DestinationType.QUEUE,
                HEADERS_EXCHANGE_X_MATCH,
                "",
                arguments2));

        Map<String, Object> arguments3 = new HashMap<>();
        arguments3.put("type", "message");
        bindings.add(new Binding(QUEUE_3,
                Binding.DestinationType.QUEUE,
                HEADERS_EXCHANGE_X_MATCH,
                "",
                arguments3));

        Map<String, Object> arguments4 = new HashMap<>();
        arguments4.put("count", "1");
        bindings.add(new Binding(QUEUE_4,
                Binding.DestinationType.QUEUE,
                HEADERS_EXCHANGE_X_MATCH,
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
     * direct binding to q5 when routing key is log5 and
     * x-dead-letter-exchange is ayah.dead.letter.exchange and
     * x-dead-letter-routing-key is dead_letter
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
        ////////////////////////////////////////////////////////////////////////////
        bindings.add(new Binding(QUEUE_5,
                Binding.DestinationType.QUEUE,
                DIRECT_EXCHANGE,
                "log5",
                null));
        return bindings;
    }

    /**
     * direct binding to dead.letter.queue when routing key is dead_letter
     * where exchange is ayah.dead.letter.exchange
     *
     * @return Binding
     */
    private static List<Binding> deadLetterBinding() {
        ArrayList<Binding> bindings = new ArrayList<>();
        bindings.add(new Binding(DEAD_LETTER_QUEUE,
                Binding.DestinationType.QUEUE,
                DEAD_LETTER_EXCHANGE,
                DEAD_LETTER_ROUTING_KEY,
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
        //////////////////////////////////////////////////////////////////////////////////////////////////////
        declarable.add(new DirectExchange(DIRECT_EXCHANGE, true, false));
        declarable.add(new TopicExchange(TOPIC_EXCHANGE, true, false));
        declarable.add(new FanoutExchange(FANOUT_EXCHANGE, true, false));
        declarable.add(new HeadersExchange(HEADERS_EXCHANGE, true, false));
        declarable.add(new HeadersExchange(HEADERS_EXCHANGE_X_MATCH, true, false));
        declarable.add(new DirectExchange(DEAD_LETTER_EXCHANGE, true, false));
        //////////////////////////////////////////////////////////////////////////////////////////////////////
        declarable.add(new Queue(DEAD_LETTER_QUEUE, true));
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE);
        arguments.put("x-dead-letter-routing-key", DEAD_LETTER_ROUTING_KEY);
        declarable.add(new Queue(QUEUE_5, true,false,false,arguments));
        ///////////////////////////////////////////////////////////////////////////////////////////////////////
        declarable.addAll(deadLetterBinding());
        declarable.addAll(directBinding());
        declarable.addAll(topicBinding());
        declarable.addAll(headersBinding());
        declarable.addAll(headersXMatchBinding());
        declarable.addAll(fanoutBinding());
        return new Declarables(declarable);
    }
}
