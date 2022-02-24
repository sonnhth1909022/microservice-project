package com.orderservice.orderservice.queue;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
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


    @Bean
    public MessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }


    @Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }
}
