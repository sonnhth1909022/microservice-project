package com.paymentservice.paymentservice.queue;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.paymentservice.paymentservice.queue.Config.QUEUE_ORDER;
import static com.paymentservice.paymentservice.queue.Config.ROUTING_KEY_ORDER;

@Component
public class Consumer {
    @RabbitListener(queues = {QUEUE_ORDER})
    public void getMessage(String message){
        System.out.println("Received topic 1 (" + ROUTING_KEY_ORDER + ") message: " + message);
    }
}
