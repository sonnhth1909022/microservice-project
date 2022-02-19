package com.orderservice.orderservice.queue;

import com.orderservice.orderservice.common.events.PaymentEvent;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.orderservice.orderservice.queue.Config.QUEUE_ORDER;
import static com.orderservice.orderservice.queue.Config.QUEUE_PAYMENT;

@Component
@Log4j2
public class ReceiverMessage {

    @Autowired
    private ConsumerService consumerService;

    @RabbitListener(queues = {QUEUE_PAYMENT})
    public void getMessagePayment(PaymentEvent paymentEvent) {
        System.out.println(paymentEvent);
        consumerService.handlerMessagePayment(paymentEvent);
    }
}
