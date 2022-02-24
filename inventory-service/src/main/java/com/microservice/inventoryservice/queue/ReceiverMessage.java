package com.microservice.inventoryservice.queue;

import com.microservice.inventoryservice.common.events.OrderEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.microservice.inventoryservice.queue.Config.QUEUE_INVENTORY;
import static com.microservice.inventoryservice.queue.Config.QUEUE_ORDER;

@Component
public class ReceiverMessage {

    @Autowired
    private ConsumerService consumerService;

    @RabbitListener(queues = {QUEUE_INVENTORY})
    public void getOrderInfo(OrderEvent orderEvent) {
        consumerService.handleInventoryCheck(orderEvent);
    }
}
