package com.orderservice.orderservice.queue;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.orderservice.orderservice.queue.Config.*;

@Component
public class ConsumerHelloTest {
    @RabbitListener(queues = {QUEUE_PAYMENT})
    public void getMessage(String message){
        System.out.println("Received topic 1 (" + ROUTING_KEY_PAYMENT + ") message: " + message);
    }
}
