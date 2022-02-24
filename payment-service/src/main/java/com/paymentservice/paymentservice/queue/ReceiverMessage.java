package com.paymentservice.paymentservice.queue;

import com.paymentservice.paymentservice.common.events.OrderEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.paymentservice.paymentservice.queue.Config.QUEUE_ORDER;

@Component
public class ReceiverMessage {

    @Autowired
    private ConsumerService consumerService;

    @RabbitListener(queues = {QUEUE_ORDER})
    public void getOrderInfo(OrderEvent orderEvent) {
        consumerService.handlerPayment(orderEvent);
    }
}
