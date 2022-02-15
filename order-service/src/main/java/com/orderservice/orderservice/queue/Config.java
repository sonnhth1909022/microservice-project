package com.orderservice.orderservice.queue;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    public static final String TOPIC_EXCHANGE = "topic.exchange";

    public static final String QUEUE_ORDER = "order.queue";
    public static final String QUEUE_PAYMENT = "payment.queue";

    public static final String ROUTING_KEY_ORDER = "order.routing.*";
    public static final String ROUTING_KEY_PAYMENT = "payment.routing.*";

    @Bean
    public Declarables topicBindings() {

        Queue queueOrder = new Queue(QUEUE_ORDER, false);
        Queue queuePayment = new Queue(QUEUE_PAYMENT, false);

        TopicExchange topicExchange = new TopicExchange(TOPIC_EXCHANGE);

        return new Declarables(
                queueOrder,
                queuePayment,
                topicExchange,
                BindingBuilder
                        .bind(queueOrder)
                        .to(topicExchange).with(ROUTING_KEY_ORDER),
                BindingBuilder
                        .bind(queuePayment)
                        .to(topicExchange).with(ROUTING_KEY_PAYMENT));
    }
}
